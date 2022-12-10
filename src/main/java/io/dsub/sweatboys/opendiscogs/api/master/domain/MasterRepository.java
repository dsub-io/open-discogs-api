package io.dsub.sweatboys.opendiscogs.api.master.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface MasterRepository {
    Mono<Page<Master>> findAllBy(Example<Master> example, Pageable pageable);

}
