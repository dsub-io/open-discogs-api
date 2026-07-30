package io.dsub.opendiscogs.api.release.infrastructure;

import io.dsub.opendiscogs.api.release.domain.Release;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ReleaseR2dbcRepository extends R2dbcRepository<Release, Long> {

}
