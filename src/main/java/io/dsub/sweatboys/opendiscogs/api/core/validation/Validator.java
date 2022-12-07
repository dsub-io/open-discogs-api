package io.dsub.sweatboys.opendiscogs.api.core.validation;

import reactor.core.publisher.Mono;

public interface Validator {

  <T> Mono<T> validate(T object);
}
