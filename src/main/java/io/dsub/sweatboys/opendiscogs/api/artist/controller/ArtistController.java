package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.application.ArtistService;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.validation.SortableParams;
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
@RequestMapping("/artists")
@Tag(name = "artists", description = "artist resource endpoints.")
public class ArtistController {

  private final ArtistService service;

  private static ArtistQuery withQuery(String name, String realName, String profile) {
    return ArtistQuery.builder().name(name).realName(realName).profile(profile).build();
  }

  @GetMapping()
  @Operation(description = "Search artists by query with AND condition. Empty strings will be ignored.")
  public Mono<ResponseEntity<PagedResponseDTO<Artist>>> searchArtists(
      @RequestParam(value = "name", required = false)
      @Schema(description = "Name to search for artist.", type = "string")
      String name,
      @RequestParam(value = "real_name", required = false)
      @Schema(description = "Real name to search for artist.", type = "string")
      String realName,
      @RequestParam(value = "profile", required = false)
      @Schema(description = "Profile to search for artist.", type = "string")
      String profile,
      @ParameterObject
      @PageableDefault
      @SortableParams({"id", "name", "real_name", "profile"})
      Pageable pageable,
      ServerHttpRequest request) {
    return service.findArtists(withQuery(name, realName, profile), pageable)
        .flatMap(dto -> Mono.fromCallable(() ->
                ResponseEntity.ok(dto.withResourceURI(request.getURI().getPath())))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  @GetMapping("/{id}")
  @Operation(description = "Get artist and details of itself.")
  public Mono<ResponseEntity<ArtistDetailDTO>> getArtist(
      @PathVariable("id")
      @Schema(description = "ID of the artist to lookup.", type = "long")
      @Valid @NotNull @Min(1) long id
  ) {
    return service.getArtist(id);
  }

  @GetMapping("/{id}/releases")
  @Operation(description = "Get artist releases from given artist by paging and sorting assist.")
  public Mono<ResponseEntity<PagedResponseDTO<ArtistReleaseDTO>>> findArtistReleases(
      @Schema(name = "id", description = "ID of the artist releases to lookup.", type = "long")
      @PathVariable(value = "id")
      Long id,
      @ParameterObject
      @PageableDefault(sort = {"id"})
      @SortableParams({"id", "title", "country", "master_id", "released_year", "released_month",
          "released_day"})
      Pageable pageable, ServerHttpRequest request) {
    return service.getArtistReleases(id, pageable)
        .flatMap(dto -> Mono.fromCallable(() ->
                ResponseEntity.ok(dto.withResourceURI(request.getURI().getPath())))
            .subscribeOn(Schedulers.boundedElastic()));
  }
}
