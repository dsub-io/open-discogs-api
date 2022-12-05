package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistService;
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

  @GetMapping
  public Mono<Page<Artist>> findArtist(Pageable pageable) {
    return service.findArtists(pageable);
  }
}
