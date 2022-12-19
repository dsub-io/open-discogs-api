package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistR2dbcRepository extends R2dbcRepository<Artist, Long> {

  @Query("""
      SELECT id, name FROM artist_group
      JOIN artist ON artist.id = artist_group.artist_id
      WHERE group_id = :id
      """)
  Flux<ArtistReferenceDTO> findMemberArtists(@Param("id") Long id);

  @Query("""
      SELECT id, name FROM artist_group
      JOIN artist ON artist.id = artist_group.group_id
      WHERE artist_id = :id
      """)
  Flux<ArtistReferenceDTO> findGroupArtists(@Param("id") Long id);

  @Query("""
      SELECT id, name
      FROM artist_alias
      JOIN artist ON artist.id = artist_alias.alias_id
      WHERE artist_id = :id
      """)
  Flux<ArtistReferenceDTO> findAliasArtists(@Param("id") Long id);

  @Query("""
      SELECT url
      FROM artist_url
      WHERE artist_id = :id
      """)
  Flux<String> findUrls(@Param("id") Long id);

  @Query("""
      SELECT name_variation
      FROM artist_name_variation
      WHERE artist_id = :id
      """)
  Flux<String> findNameVariations(@Param("id") Long id);


  @Query("""
      SELECT * FROM (SELECT release.*, 'Main' AS role
      FROM release_artist AS ra
      JOIN release ON id = ra.release_id
            WHERE artist_id = :id
            UNION ALL
            SELECT release.*, role
            FROM release_credited_artist AS rca
                JOIN release ON id = rca.release_id
            WHERE artist_id = :id) t
      ORDER BY :sort
      OFFSET :offset LIMIT :limit;
      """)
  Flux<ArtistReleaseDTO> findReleasesByArtistId(
      @Param("id") Long id,
      @Param("offset") long offset,
      @Param("limit") int limit,
      @Param("sort") String sort);

  @Query("""
      SELECT (
      (SELECT COUNT(*)
      FROM release_artist AS ra
      LEFT JOIN release ON id = ra.release_id
      WHERE artist_id = :id)
      +
      (SELECT COUNT(*)
      FROM release_credited_artist AS rca
      LEFT JOIN release ON id = rca.release_id
      WHERE artist_id = :id)
      );
      """)
  Mono<Long> countReleasesByArtistId(@Param("id") Long id);
}
