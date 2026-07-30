package io.dsub.opendiscogs.api.label.infrastructure;

import io.dsub.opendiscogs.api.label.domain.Label;
import io.dsub.opendiscogs.api.label.dto.LabelReferenceDTO;
import io.dsub.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.opendiscogs.api.label.projection.LabelDetailProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LabelR2dbcRepository extends R2dbcRepository<Label, Long> {

  @Query("""
      SELECT child.id, child.name
      FROM label_sub_label relation
      JOIN label child ON child.id = relation.sub_label_id
      WHERE relation.parent_label_id = :id
      ORDER BY child.id
      """)
  Flux<LabelReferenceDTO> findSubLabels(@Param("id") Long id);

  @Query("""
      SELECT label.id,
             label.contact_info,
             label.data_quality,
             label.name,
             label.profile,
             parent.id AS parent_label_id,
             parent.name AS parent_label_name
      FROM label
      LEFT JOIN label_sub_label relation ON relation.sub_label_id = label.id
      LEFT JOIN label parent ON parent.id = relation.parent_label_id
      WHERE label.id = :id
      ORDER BY parent.id
      LIMIT 1
      """)
  Mono<LabelDetailProjection> getLabelDetailById(@Param("id") Long id);

  @Query("SELECT url FROM label_url WHERE label_id = :id")
  Flux<String> findUrls(@Param("id") Long id);

  @Query("""
              SELECT r.id AS id,
                     r.status AS status,
                     r.title AS title,
                     CASE WHEN r.has_valid_year THEN EXTRACT(YEAR FROM r.release_date) END AS year,
                     string_agg(DISTINCT rf.description, ',') AS format,
                     lr.category_notation AS catno,
                     string_agg(DISTINCT a.name, ',') AS artist
              FROM label_release_item AS lr
                       JOIN release_item r ON r.id = lr.release_item_id
                       LEFT JOIN release_item_artist ra ON r.id = ra.release_item_id
                       LEFT JOIN artist a ON ra.artist_id = a.id
                       LEFT JOIN release_item_format rf ON r.id = rf.release_item_id
              WHERE lr.label_id = :id
              GROUP BY r.id, lr.category_notation
              ORDER BY r.id
              OFFSET :offset LIMIT :limit;
      """)
  Flux<LabelReleaseDTO> findReleasesByLabelId(
      @Param("id") Long id,
      @Param("offset") long offset,
      @Param("limit") int limit
  );

  @Query("""
          SELECT COUNT(DISTINCT release_item_id)
          FROM label_release_item
          WHERE label_id = :id
      """)
  Mono<Long> countReleasesByLabelId(@Param("id") Long id);
}
