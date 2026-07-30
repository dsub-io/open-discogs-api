package io.dsub.opendiscogs.api.artist.domain;

import io.dsub.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistRepository {

  Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable);

  Mono<ArtistDetailDTO> findById(Long id);

  Flux<ArtistReleaseDTO> findReleasesByArtistId(Long id, Pageable pageable);

  Mono<Long> countReleasesByArtistId(Long id);
}
