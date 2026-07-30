package io.dsub.opendiscogs.api.release.application;

import io.dsub.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.opendiscogs.api.release.domain.ReleaseRepository;
import io.dsub.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.opendiscogs.api.release.query.ReleaseQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public Mono<PagedResponseDTO<ReleaseDTO>> search(ReleaseQuery query, Pageable pageable) {
    return releaseRepository.findAllBy(query.normalized(), pageable)
        .flatMap(PagedResponseDTO::fromPage);
  }

  public Mono<ReleaseDetailDTO> getReleaseById(Long id) {
    return releaseRepository.getById(id)
        .switchIfEmpty(Mono.error(new ItemNotFoundException("release", id)));
  }
}
