package io.dsub.sweatboys.opendiscogs.api.release.controller;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.validation.SortableParams;
import io.dsub.sweatboys.opendiscogs.api.release.application.ReleaseService;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("releases")
@RequiredArgsConstructor
public class ReleaseController {
    private final ReleaseService service;
    @GetMapping
    public Mono<PagedResponseDTO<ReleaseDTO>> search(
            @RequestParam(value = "title", required = false)
            @Schema(description = "Title of release", type = "string")
            String title,
            @RequestParam(value = "country", required = false)
            @Schema(description = "Released country", type = "string")
            String country,
            @RequestParam(value = "year", required = false)
            @Schema(description = "Released year", type = "integer")
            @Range(min = 0, max = 32767)
            Integer year,
            @RequestParam(value = "month", required = false)
            @Schema(description = "Released month", type = "integer")
            @Range(min = 0, max = 32767)
            Integer month,
            @RequestParam(value = "master", required = false)
            @Schema(description = "Valid only if main release", type = "boolean")
            Boolean isMaster,
            @ParameterObject
            @PageableDefault(sort = {"id"})
            @SortableParams({"id", "title", "country", "released_year", "released_month"})
            Pageable pageable
    ) {
        return service.search(withQuery(title, country, year, month, isMaster), pageable);
    }
    @GetMapping("/{id}")
    public Mono<ReleaseDetailDTO> getById(@PathVariable("id") Long id) {
        return service.getReleaseById(id).switchIfEmpty(Mono.error(new ItemNotFoundException("release", id)));
    }
    private static ReleaseQuery withQuery(String title, String country, Integer year, Integer month, Boolean isMaster) {
        return new ReleaseQuery(title, country, year, month, isMaster);
    }
}