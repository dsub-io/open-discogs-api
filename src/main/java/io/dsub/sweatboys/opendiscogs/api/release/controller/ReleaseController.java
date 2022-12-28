package io.dsub.sweatboys.opendiscogs.api.release.controller;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.validation.SortableParams;
import io.dsub.sweatboys.opendiscogs.api.release.application.ReleaseService;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/releases")
@RequiredArgsConstructor
@Tag(name = "releases", description = "release resource endpoints.")
public class ReleaseController {
  private final ReleaseService service;

  private static ReleaseQuery withQuery(String title, String country, Integer year, Integer month,
      Boolean isMaster) {
    return new ReleaseQuery(title, country, year, month, isMaster);
  }

  @GetMapping()
  @Operation(description = "Search releases by query with AND condition. Empty strings will be ignored.")
  public Mono<PagedResponseDTO<ReleaseDTO>> search(
      @RequestParam(value = "title", required = false)
      @Schema(description = "Title of release to lookup releases.", type = "string")
      String title,
      @RequestParam(value = "country", required = false)
      @Schema(description = "Released country to lookup releases.", type = "string")
      String country,
      @RequestParam(value = "year", required = false)
      @Schema(description = "Released year to lookup releases.", type = "integer")
      @Range(min = 0, max = 32767)
      Integer year,
      @RequestParam(value = "month", required = false)
      @Schema(description = "Released month to lookup releases", type = "integer")
      @Range(min = 1, max = 12)
      Integer month,
      @RequestParam(value = "master", required = false)
      @Schema(description = "Valid only if main release.", type = "boolean")
      Boolean master,
      @ParameterObject
      @PageableDefault(sort = {"id"})
      @SortableParams({"id", "title", "country", "released_year", "released_month"})
      Pageable pageable
  ) {
    return service.search(withQuery(title, country, year, month, master), pageable);
  }

  @GetMapping("/{id}")
  @Operation(description = "Get release and details of itself.")
  public Mono<ReleaseDetailDTO> getById(
      @PathVariable("id")
      @Schema(description = "ID of the release to lookup.", type = "long")
      Long id) {
    return service.getReleaseById(id)
        .switchIfEmpty(Mono.error(new ItemNotFoundException("release", id)));
  }
}
