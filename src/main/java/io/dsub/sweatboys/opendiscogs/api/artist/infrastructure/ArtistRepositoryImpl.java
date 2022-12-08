package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.repository.query.FluentQuery.ReactiveFluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {

  private final ArtistR2dbcRepository delegate;
  private final R2dbcEntityTemplate template;

  @Override
  public Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable) {
    return delegate.findBy(example, getPageableQueryFunction(pageable));
  }

  @Override
  public Mono<Page<Artist>> findById(Example<Artist> example, Pageable pageable) {
    return delegate.findBy(example, getPageableQueryFunction(pageable));
  }

  private static Function<ReactiveFluentQuery<Artist>, Mono<Page<Artist>>> getPageableQueryFunction(
      Pageable pageable) {
    return p -> p.as(Artist.class).page(pageable);
  }
}