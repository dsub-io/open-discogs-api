package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistR2dbcRepository extends R2dbcRepository<Artist, Long> {}