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
  @Query("SELECT id, name FROM artist_group "
      + "LEFT JOIN artist ON id = artist_group.artist_id "
      + "WHERE group_id = :id")
  Flux<ArtistReferenceDTO> findMemberArtists(@Param("id") Long id);

  @Query("SELECT id, name FROM artist_group "
      + "LEFT JOIN artist ON artist.id = artist_group.group_id "
      + "WHERE artist_id = :id")
  Flux<ArtistReferenceDTO> findGroupArtists(@Param("id") Long id);

  @Query("SELECT id, name FROM artist_alias LEFT JOIN artist ON artist.id = artist_alias.alias_id WHERE artist_id = :id" )
  Flux<ArtistReferenceDTO> findAliasArtists(@Param("id") Long id);

  @Query("SELECT url FROM artist_url WHERE artist_id = :id")
  Flux<String> findUrls(@Param("id") Long id);

  @Query("SELECT name_variation FROM artist_name_variation WHERE artist_id = :id")
  Flux<String> findNameVariations(@Param("id") Long id);


  @Query("SELECT * \n"
      + "FROM (SELECT release_id, artist_id, id, title, country, data_quality, released_year, released_month, released_day, listed_release_date, is_master, notes, status, 'Main' AS role\n"
      + "      FROM release_artist AS ra\n"
      + "               LEFT JOIN release ON id = ra.release_id\n"
      + "      WHERE artist_id = :id\n"
      + "      UNION\n"
      + "      SELECT release_id, artist_id, id, title, country, data_quality, released_year, released_month, released_day, listed_release_date, is_master, notes, status, role\n"
      + "      FROM release_credited_artist AS rca\n"
      + "               LEFT JOIN release ON id = rca.release_id\n"
      + "      WHERE artist_id = :id) t\n"
      + "ORDER BY :sort\n"
      + "OFFSET :offset LIMIT :limit;")
  Flux<ArtistReleaseDTO> findReleasesByArtistId(
      @Param("id") Long id,
      @Param("offset") long offset,
      @Param("limit") int limit,
      @Param("sort") String sort);

  @Query("SELECT ("
      + "(SELECT COUNT(*) "
      + "FROM release_artist AS ra "
      + "LEFT JOIN release ON id = ra.release_id "
      + "WHERE artist_id = :id) "
      + "+ "
      + "(SELECT COUNT(*) "
      + "FROM release_credited_artist AS rca "
      + "LEFT JOIN release ON id = rca.release_id "
      + "WHERE artist_id = :id) "
      + ");")
  Mono<Long> countReleasesByArtistId(@Param("id") Long id);
}
