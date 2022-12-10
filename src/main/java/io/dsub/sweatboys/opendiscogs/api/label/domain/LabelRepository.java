package io.dsub.sweatboys.opendiscogs.api.label.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface LabelRepository {
    Mono<Page<Label>> findAllBy(Example<Label> example, Pageable pageable);

}
