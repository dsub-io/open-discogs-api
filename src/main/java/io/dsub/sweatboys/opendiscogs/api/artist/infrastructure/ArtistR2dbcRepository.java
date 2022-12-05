package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ArtistR2dbcRepository extends R2dbcRepository<Artist, Long> {
  Flux<Artist> findAllBy(Pageable pageable);
}
