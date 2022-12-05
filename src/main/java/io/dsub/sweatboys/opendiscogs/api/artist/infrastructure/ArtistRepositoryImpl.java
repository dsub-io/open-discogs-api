package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {
  private final ArtistR2dbcRepository delegate;

  @Override
  public Flux<Artist> findAllBy(Pageable pageable) {
    return delegate.findAllBy(pageable);
  }

  @Override
  public Mono<Long> count() {
    return delegate.count();
  }
}