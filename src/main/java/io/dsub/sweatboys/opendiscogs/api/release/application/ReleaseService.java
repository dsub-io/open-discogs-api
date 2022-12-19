package io.dsub.sweatboys.opendiscogs.api.release.application;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.domain.ReleaseRepository;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository releaseRepository;
    public Mono<PagedResponseDTO<ReleaseDTO>> search(ReleaseQuery query, Pageable pageable) {
        return releaseRepository.findAllBy(Example.of(query.toRelease(),
                        matchingAll()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                .withIgnoreCase()
                                .withIgnoreNullValues()), pageable)
                .flatMap(PagedResponseDTO::fromPage);
    }
    public Mono<ReleaseDetailDTO> getReleaseById(Long id) {
        return releaseRepository.getById(id)
            .switchIfEmpty(Mono.error(new ItemNotFoundException("release", id)));
    }
}
