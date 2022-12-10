package io.dsub.sweatboys.opendiscogs.api.release.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ReleaseRepository {
    Mono<Page<Release>> findAllBy(Example<Release> example, Pageable pageable);
}
