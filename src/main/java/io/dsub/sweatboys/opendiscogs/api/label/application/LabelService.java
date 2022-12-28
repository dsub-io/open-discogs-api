package io.dsub.sweatboys.opendiscogs.api.label.application;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import java.util.Collections;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LabelService {

  private final LabelRepository labelRepository;

  private static <T> Mono<T> getItemNotFoundException(long id) {
    return Mono.error(new ItemNotFoundException("label", id));
  }

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
        .switchIfEmpty(getItemNotFoundException(id));
  }

  public Mono<PagedResponseDTO<LabelReleaseDTO>> getLabelReleases(long id, Pageable pageable) {
    return labelRepository.findReleasesByLabelId(id, pageable)
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .zipWith(labelRepository.countReleasesByLabelId(id))
        .flatMap(tuple -> PagedResponseDTO.fromPage(
            new PageImpl<>(tuple.getT1(), pageable, tuple.getT2())));
  }
}
