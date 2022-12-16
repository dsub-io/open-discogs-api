package io.dsub.sweatboys.opendiscogs.api.validation;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class ValidatorImpl implements Validator {
  private final jakarta.validation.Validator delegate;
  @Override
  public <T> Mono<T> validate(T object) {
    return Mono.fromCallable(() -> delegate.validate(object))
        .flatMap(violations -> violations.isEmpty() ?
            Mono.just(object) : Mono.error(new ConstraintViolationException(violations)))
        .subscribeOn(Schedulers.boundedElastic());
  }
}