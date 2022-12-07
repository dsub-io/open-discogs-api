package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistService;
import io.dsub.sweatboys.opendiscogs.api.core.response.ResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@Validated
@RestController
@RequiredArgsConstructor
public class ArtistController {
  private final ArtistService service;
  @GetMapping("artist")
  public Mono<ResponseDTO<Artist>> searchArtists(ArtistQuery query, Pageable pageable) {
    return service.findArtists(query, pageable).
        flatMap(dto -> Mono.just(dto.withResourceURI("/artist")));
  }

  @GetMapping("artist/{id}")
  public Mono<ResponseDTO<Artist>> getArtist(@PathVariable("id") @Valid @NotNull @Min(1) long id) {
    return service.findArtist(id).flatMap(dto -> Mono.just(dto.withResourceURI("/artist/" + id).withPageNumber(dto.getPageNumber() + 1)));
  }
}