package io.dsub.sweatboys.opendiscogs.api.artist.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistRepository {
  Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable);
  Mono<Page<Artist>> findById(Example<Artist> example, Pageable pageable);
}
