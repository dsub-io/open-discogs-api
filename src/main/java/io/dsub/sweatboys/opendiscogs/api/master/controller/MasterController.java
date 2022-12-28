package io.dsub.sweatboys.opendiscogs.api.master.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.validation.SortableParams;
import io.dsub.sweatboys.opendiscogs.api.master.application.MasterService;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
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
@RequestMapping("/masters")
@RequiredArgsConstructor
@Tag(name = "masters", description = "master resource endpoints.")
public class MasterController {

    private final MasterService service;

    private static MasterQuery withQuery(String title, Integer year) {
        return MasterQuery.builder()
                .title(title)
                .year(year)
                .build();
    }

    @GetMapping
    @Operation(description = "Search master releases by query AND condition. Empty strings will be ignored.")
    public Mono<PagedResponseDTO<Master>> search(
            @RequestParam(value = "title", required = false)
            @Schema(description = "Title to be contained.", implementation = String.class)
            String title,
            @RequestParam(value = "year", required = false)
            @Schema(description = "Year to search.", implementation = Integer.class)
            Integer year,
            @ParameterObject
            @PageableDefault(sort = {"id"})
            @SortableParams({"id", "title", "released_year"})
            Pageable pageable,
            ServerHttpRequest request) {
        return service.findMasters(withQuery(title, year), pageable)
                .flatMap(dto -> Mono.fromCallable(() -> dto.withResourceURI(request.getURI().getPath()))
                        .publishOn(Schedulers.boundedElastic()));
    }

    @GetMapping("/{id}")
    @Operation(description = "Get master and details of itself.")
    public Mono<ResponseEntity<MasterDetailDTO>> getMaster(
            @PathVariable("id")
            @Schema(description = "ID of the master release to lookup.", implementation = Long.class)
            long id
    ) {
        return service.getById(id)
                .flatMap(dto -> Mono.fromCallable(() -> ResponseEntity.ok(dto))
                        .subscribeOn(Schedulers.boundedElastic()));
    }

    @GetMapping("/{id}/releases")
    @Operation(description = "Get master releases from given master by paging and sorting assist.")
    public Mono<PagedResponseDTO<MasterReleaseDTO>> getMasterReleases(
            @PathVariable("id")
            @Schema(description = "ID of the master release to lookup.", implementation = Long.class)
            long id,
            @ParameterObject
            @PageableDefault
            Pageable pageable) {
        return service.getMasterSubReleases(id, pageable);
    }
}
