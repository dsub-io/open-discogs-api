package io.dsub.sweatboys.opendiscogs.api.artist.service;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.core.service.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArtistService implements PagingService {
  private final ArtistRepository artistRepository;

  public Mono<Page<Artist>> findArtists(Pageable pageable) {
    return getPagedResult(artistRepository.count(), pageable, artistRepository.findAllBy(pageable));
  }
}
