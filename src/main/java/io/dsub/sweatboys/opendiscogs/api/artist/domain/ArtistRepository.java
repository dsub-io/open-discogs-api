package io.dsub.sweatboys.opendiscogs.api.artist.domain;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistRepository {
  Flux<Artist> findAllBy(Pageable pageable);
  Mono<Long> count();
}
