package io.dsub.sweatboys.opendiscogs.api.release.domain;

import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ReleaseRepository {
    Mono<Page<ReleaseDTO>> findAllBy(Example<Release> example, Pageable pageable);
    Mono<ReleaseDetailDTO> getById(Long id);
}