package io.dsub.sweatboys.opendiscogs.api.master.controller;

import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.application.MasterService;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public Mono<PagedResponseDTO<Master>> search(
      @ParameterObject MasterQuery query,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    return service.findMasters(query, pageable);
  }

  @GetMapping("/{id}")
  @Operation(description = "")
  public Mono<ResponseEntity<MasterDetailDTO>> getMaster(@PathVariable("id") long id) {
    return service.getById(id)
        .flatMap(dto -> Mono.fromCallable(() -> ResponseEntity.ok(dto))
            .subscribeOn(Schedulers.boundedElastic()));
  }
}
