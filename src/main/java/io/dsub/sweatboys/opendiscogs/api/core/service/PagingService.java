package io.dsub.sweatboys.opendiscogs.api.core.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public interface PagingService {
  default <T> Mono<Page<T>> getPagedResult(List<T> items, Pageable pageable) {
    return Mono.fromCallable(() -> new PageImpl<>(items, pageable, items.size()))
        .subscribeOn(Schedulers.boundedElastic())
        .flatMap(Mono::just);
  }
}