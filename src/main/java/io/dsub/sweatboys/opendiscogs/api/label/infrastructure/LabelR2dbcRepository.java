package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.projection.LabelDetailProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LabelR2dbcRepository extends R2dbcRepository<Label, Long> {
    @Query("SELECT id, name FROM label WHERE parent_id = :id")
    Flux<LabelReferenceDTO> findSubLabels(@Param("id") Long id);

    @Query("""
            SELECT id, contact_info, data_quality, name, profile, parent_id, parent_label_id, parent_label_name FROM label 
                        LEFT JOIN (SELECT id AS parent_label_id, name AS parent_label_name FROM label) parent
                        ON  parent_label_id = label.parent_id
                        WHERE label.id = :id
            """)
    Mono<LabelDetailProjection> getLabelDetailById(@Param("id") Long id);

    @Query("SELECT url FROM label_url WHERE label_id = :id")
    Flux<String> findUrls(@Param("id") Long id);

    @Query("""
        SELECT r.id                 AS id,
               r.status             AS status,
               r.title              AS title,
               r.released_year      AS year,
               rf.description       AS format,
               lr.category_notation AS catno,
               a.name               AS artist
        FROM label_release as lr
                 LEFT JOIN release r ON r.id = lr.release_id
                 LEFT JOIN release_artist ra on r.id = ra.release_id
                 LEFT JOIN artist a on ra.artist_id = a.id
                 LEFT JOIN release_format rf on r.id = rf.release_id
        WHERE lr.label_id = :id
        ORDER BY :sort
        OFFSET :offset LIMIT :limit;
""")
    Flux<LabelReleaseDTO> findReleasesByLabelId(
            @Param("id") Long id,
            @Param("offset") long offset,
            @Param("limit") int limit,
            @Param("sort") String sort
    );

    @Query("""
        SELECT COUNT(*)
        FROM label_release as lr
                 LEFT JOIN release r ON r.id = lr.release_id
                 LEFT JOIN release_artist ra on r.id = ra.release_id
                 LEFT JOIN artist a on ra.artist_id = a.id
                 LEFT JOIN release_format rf on r.id = rf.release_id
        WHERE lr.label_id = :id 
    """)
    Mono<Long> countReleasesByLabelId(@Param("id") Long id);
}