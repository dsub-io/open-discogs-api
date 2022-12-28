package io.dsub.sweatboys.opendiscogs.api.label.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.application.LabelService;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
@Tag(name = "labels", description = "label resource endpoints.")
public class LabelController {

  private final LabelService labelService;

  private static <T> Mono<ResponseEntity<PagedResponseDTO<T>>> wrapResponse(PagedResponseDTO<T> dto,
      ServerHttpRequest req) {
    return Mono.fromCallable(() -> dto.withResourceURI(req.getURI().getPath()))
        .map(ResponseEntity::ok)
        .subscribeOn(Schedulers.boundedElastic());
  }

  private static LabelQuery withQuery(String contactInfo, String dataQuality, String name,
      String profile) {
    return LabelQuery.builder()
        .contactInfo(contactInfo)
        .dataQuality(dataQuality)
        .name(name)
        .profile(profile)
        .build();
  }

  @GetMapping("")
  @Operation(description = "Search labels by query with AND condition. Empty strings will be ignored.")
  public Mono<ResponseEntity<PagedResponseDTO<Label>>> searchLabels(
      @RequestParam(value = "contact_info", required = false)
      @Schema(description = "Contact info of label to lookup labels.", type ="string")
      String contactInfo,
      @RequestParam(value = "data_quality", required = false)
      @Schema(description = "Data Quality of label to lookup labels.", type = "string")
      String dataQuality,
      @RequestParam(value = "name", required = false)
      @Schema(description = "Name of label to lookup labels.", type = "string")
      String name,
      @RequestParam(value = "profile", required = false)
      @Schema(description = "Profile to search for label.", type = "string")
      String profile,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable,
      ServerHttpRequest request) {
    return labelService
        .findLabels(withQuery(contactInfo, dataQuality, name, profile), pageable)
        .flatMap(dto -> wrapResponse(dto, request));
  }

  @GetMapping("/{id}")
  @Operation(description = "Get label and details of itself.")
  public Mono<ResponseEntity<LabelDetailDTO>> getLabel(
      @PathVariable("id")
      @Schema(description = "ID of the label to lookup.", implementation = Long.class)
      long id
  ) {
    return labelService.getLabel(id);
  }

  @GetMapping("/{id}/releases")
  @Operation(description = "Get label releases from given label by paging and sorting assist.")
  public Mono<ResponseEntity<PagedResponseDTO<LabelReleaseDTO>>> findLabelReleases(
      @Schema(name = "id", implementation = Long.class, description = "ID of the label releases to lookup")
      @PathVariable(value = "id")
      Long id,
      @ParameterObject
      @PageableDefault(sort = {"id"})
      Pageable pageable,
      ServerHttpRequest request) {
    return labelService
        .getLabelReleases(id, pageable)
        .flatMap(dto -> wrapResponse(dto, request));
  }
}
