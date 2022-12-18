package io.dsub.sweatboys.opendiscogs.api.release.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.application.ReleaseService;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("releases")
@RequiredArgsConstructor
public class ReleaseController {
    private final ReleaseService service;

    @GetMapping
    public Mono<PagedResponseDTO<Release>> search(
            @RequestParam(value = "title", required = false)
            @Schema(description = "Title of release")
            String title,
            @RequestParam(value = "country", required = false)
            @Schema(description = "Released country")
            String country,
            @RequestParam(value = "year", required = false)
            @Schema(description = "Released year")
            @Range(min = 0, max = 32767)
            Integer year,
            @RequestParam(value = "month", required = false)
            @Schema(description = "Released month")
            @Range(min = 0, max = 32767)
            Integer month,
            @RequestParam(value = "is_master", required = false)
            @Schema(description = "Valid only if main release", type = "boolean")
            Boolean isMaster,
            @ParameterObject
            @PageableDefault(sort = {"id"})
            Pageable pageable
    ) {
        return null;
    }

    private static ReleaseQuery withQuery(String title, String country, Integer year, Integer month, Boolean isMaster) {
        return new ReleaseQuery(title, country, year, month, isMaster);
    }
}