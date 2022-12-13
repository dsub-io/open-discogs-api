package io.dsub.sweatboys.opendiscogs.api.release.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.release.domain.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepository {
  private final ReleaseR2dbcRepository delegate;

  @Override
  public Mono<Page<Release>> findAllBy(Example<Release> example, Pageable pageable) {
    return delegate.findBy(example, q -> q.page(pageable));
  }
}
