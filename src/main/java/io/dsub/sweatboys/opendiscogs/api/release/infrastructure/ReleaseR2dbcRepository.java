package io.dsub.sweatboys.opendiscogs.api.release.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ReleaseR2dbcRepository extends R2dbcRepository<Release, Long> {
}
