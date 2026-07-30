package io.dsub.opendiscogs.api.release.domain;

import io.dsub.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.opendiscogs.api.release.query.ReleaseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ReleaseRepository {

  Mono<Page<ReleaseDTO>> findAllBy(ReleaseQuery query, Pageable pageable);

  Mono<ReleaseDetailDTO> getById(Long id);
}
