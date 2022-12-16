package io.dsub.sweatboys.opendiscogs.api.master.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.application.MasterService;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/masters")
@RequiredArgsConstructor
public class MasterController {

  private final MasterService service;

  @GetMapping
  @Operation(description = "Search master releases by query AND condition. Empty strings will be ignored.")
  public Mono<ResponseEntity<PagedResponseDTO<Master>>> search(
      @RequestParam(value = "title", required = false)
      @Schema(description = "Title to be contained")
      String title,
      @RequestParam(value = "year", required = false)
      @Schema(description = "Year to search")
      Integer year,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable,
      ServerHttpRequest request) {
    return service.findMasters(withQuery(title, year), pageable)
        .flatMap(dto -> Mono.fromCallable(() ->
                ResponseEntity.ok(dto.withResourceURI(request.getURI().getPath())))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private static MasterQuery withQuery(String title, Integer year) {
    return MasterQuery.builder()
        .title(title)
        .year(year)
        .build();
  }

  @GetMapping("/{id}")
  @Operation(description = "Get master and details of itself")
  public Mono<ResponseEntity<MasterDetailDTO>> getMaster(
      @PathVariable("id")
      @Schema(description = "ID of the master release to lookup.")
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
      @Schema(description = "ID of the master release to lookup.")
      long id,
      @ParameterObject
      @PageableDefault
      Pageable pageable) {
    return service.getMasterSubReleases(id, pageable);
  }
}
