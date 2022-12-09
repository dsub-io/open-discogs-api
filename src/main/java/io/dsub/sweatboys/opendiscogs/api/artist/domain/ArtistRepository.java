package io.dsub.sweatboys.opendiscogs.api.artist.domain;

import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ArtistRepository  {
  Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable);

  Mono<ArtistDetailDTO> findById(Long id);
}
