package io.dsub.sweatboys.opendiscogs.api.core.service;

import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PagingService {
  default <T> Mono<Page<T>> getPagedResult(Mono<Long> count, Pageable pageable, Flux<T> items) {
    return count.flatMap(c -> items
        .buffer(pageable.getPageSize(), pageable.getPageNumber() + 1)
        .elementAt(pageable.getPageNumber(), new ArrayList<>())
        .map(bufferedItems -> new PageImpl<>(bufferedItems, pageable, c)));
  }
}