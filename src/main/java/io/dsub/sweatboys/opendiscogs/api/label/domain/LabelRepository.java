package io.dsub.sweatboys.opendiscogs.api.label.domain;

import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface LabelRepository {
    Mono<Page<Label>> findAllBy(Example<Label> example, Pageable pageable);

    Mono<LabelDetailDTO> findById(Long id);
}
