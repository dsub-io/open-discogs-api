package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistService;
import io.dsub.sweatboys.opendiscogs.api.core.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {
  private final ArtistService service;
  @GetMapping("artist")
  public Mono<ResponseDTO<Artist>> findArtist(ArtistQuery query, Pageable pageable) {
    return service.findArtists(query, pageable);
  }
}
