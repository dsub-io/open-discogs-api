package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistService;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
@Tag(name = "artists", description = "artist resource endpoints")
public class ArtistController {

  private final ArtistService service;

  @GetMapping
  @Operation(description = "Search artists by query with AND condition. Empty strings will be ignored.", responses = {
      @ApiResponse(description = "On Bad Request", useReturnTypeSchema = true, responseCode = "400")
  })
  public Mono<PagedResponseDTO<Artist>> searchArtists(@ParameterObject ArtistQuery query,
      @ParameterObject @PageableDefault(value = 30, page = 1) Pageable pageable,
      ServerHttpRequest serverHttpRequest) {
    return service.findArtists(query, pageable).
        map(dto -> dto.withResourceURI(serverHttpRequest.getURI().toString()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ArtistDetailDTO>> getArtist(@PathVariable("id") @Valid @NotNull @Min(1) long id) {
    return service.getArtist(id);
  }
}
