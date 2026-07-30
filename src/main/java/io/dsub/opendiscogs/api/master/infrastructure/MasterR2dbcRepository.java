package io.dsub.opendiscogs.api.master.infrastructure;

import io.dsub.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.opendiscogs.api.master.domain.Master;
import io.dsub.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.opendiscogs.api.master.dto.MasterVideoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MasterR2dbcRepository extends R2dbcRepository<Master, Long> {

  @Query("""
      SELECT style FROM master_style
      WHERE master_id = :id
      """)
  Flux<String> findMasterStyles(@Param("id") Long id);

  @Query("""
      SELECT genre FROM master_genre
      WHERE master_id = :id
      """)
  Flux<String> findMasterGenres(@Param("id") Long id);

  @Query("""
      SELECT artist.id, artist.name FROM master_artist
      JOIN artist on artist_id = artist.id
      WHERE master_id = :id
      """)
  Flux<ArtistReferenceDTO> findMasterArtists(@Param("id") Long id);

  @Query("""
      SELECT main_release_id FROM master
      WHERE id = :id
      """)
  Mono<Long> findMainReleaseId(@Param("id") Long id);

  @Query("""
      SELECT r.id,
             r.title,
             string_agg(a.name, ',') AS artist,
             CASE WHEN r.has_valid_year THEN EXTRACT(YEAR FROM r.release_date) END AS year,
             array_to_string(array_agg(DISTINCT a.id), ',') AS artist_id
      FROM release_item r
               JOIN release_item_artist ra ON r.id = ra.release_item_id
               JOIN artist a on a.id = ra.artist_id
      WHERE r.master_id = :id
      GROUP BY r.id
      ORDER BY r.id
      OFFSET :offset LIMIT :limit
      """)
  Flux<MasterReleaseDTO> findReleasesByMasterId(
      @Param("id") Long id,
      @Param("offset") Long offset,
      @Param("limit") int limit);

  @Query("""
      SELECT count(*) FROM release_item
      WHERE master_id = :id
      """)
  Mono<Long> countReleasesByMasterId(@Param("id") Long id);

  @Query("""
      SELECT url, description, title FROM master_video
      WHERE master_id = :id
      """)
  Flux<MasterVideoDTO> findMasterVideos(@Param("id") Long id);
}
