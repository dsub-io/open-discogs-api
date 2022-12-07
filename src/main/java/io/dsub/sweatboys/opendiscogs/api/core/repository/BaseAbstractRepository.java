package io.dsub.sweatboys.opendiscogs.api.core.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public abstract class BaseAbstractRepository<T, ID> {
  protected final R2dbcRepository<T, ID> delegate;
  private final Class<T> clazz;

  protected BaseAbstractRepository(R2dbcRepository<T, ID> delegate, Class<T> clazz) {
    this.delegate = delegate;
    this.clazz = clazz;
  }

  public Mono<Page<T>> FindAllBy(Example<T> example, Pageable pageable) {
    return delegate.findBy(example, p -> p.as(clazz).page(pageable));
  }
}
