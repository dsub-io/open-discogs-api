package io.dsub.sweatboys.opendiscogs.api.label.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.application.LabelService;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
@Tag(name = "labels", description = "label resource endpoints")
public class LabelController {

    private final LabelService labelService;

    @GetMapping("")
    @Operation(description = "")
    public Mono<ResponseEntity<PagedResponseDTO<Label>>> searchLabels (
        @RequestParam(value = "contact_info", required = false)
        @Schema(description = "Contact info to search for label")
        String contactInfo,
        @RequestParam(value = "data_quality", required = false)
        @Schema(description = "Data Quality to search for label")
        String dataQuality,
        @RequestParam(value = "name",required = false)
        @Schema(description = "Name to search for label")
        String name,
        @RequestParam(value = "profile", required = false)
        @Schema(description = "Profile to search for label")
        String profile,
        @ParameterObject @PageableDefault(page = 1) Pageable pageable,
        ServerHttpRequest serverHttpRequest) {
        return labelService.findLabels(withQuery(contactInfo, dataQuality, name, profile), pageable)
                .map(dto -> dto.withResourceURI(serverHttpRequest.getURI().getPath()))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get details by label id")
    public Mono<ResponseEntity<LabelDetailDTO>> getLabel(@PathVariable("id") @Valid @NotNull @Min(1) long id) {
        return labelService.getLabel(id);
    }

    private static LabelQuery withQuery(String contactInfo, String dataQuality, String name, String profile) {
        return LabelQuery.builder()
                .contactInfo(contactInfo)
                .dataQuality(dataQuality)
                .name(name)
                .profile(profile)
                .build();
    }

}
