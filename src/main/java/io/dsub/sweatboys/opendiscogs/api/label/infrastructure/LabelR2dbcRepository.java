package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReferenceDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface LabelR2dbcRepository extends R2dbcRepository<Label, Long> {

    @Query("SELECT id, name FROM label AS l WHERE l.parent_id = :id")
    Flux<LabelReferenceDTO> findSublabels(@Param("id") Long id);

    @Query("SELECT url FROM label_url WHERE label_id = :id")
    Flux<String> findUrls(@Param("id") Long id);
}
