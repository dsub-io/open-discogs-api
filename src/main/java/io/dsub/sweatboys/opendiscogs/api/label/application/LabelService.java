package io.dsub.sweatboys.opendiscogs.api.label.application;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public Mono<PagedResponseDTO<Label>> findLabels(LabelQuery query, Pageable pageable) {
        return labelRepository.findAllBy(Example.of(query.toLabel(),
                matchingAll()
                        .withStringMatcher(StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues()), pageable)
                .flatMap(fromPageToResponseDTO());
    }

    private Function<Page<Label>, Mono<PagedResponseDTO<Label>>> fromPageToResponseDTO() {
        return PagedResponseDTO::fromPage;
    }

    public Mono<ResponseEntity<LabelDetailDTO>> getLabel(long id) {
        return labelRepository.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(getItemNotfoundException(id));
    }

    private static Mono<ResponseEntity<LabelDetailDTO>> getItemNotfoundException(long id) {
        return Mono.error(new ItemNotFoundException("label", id));
    }
}
