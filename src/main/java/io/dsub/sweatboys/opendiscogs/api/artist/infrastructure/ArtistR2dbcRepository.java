package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface ArtistR2dbcRepository extends R2dbcRepository<Artist, Long> {
  @Query("SELECT id, name FROM artist AS member "
      + "LEFT JOIN artist_group ag ON member.id = ag.artist_id "
      + "WHERE ag.group_id = :id")
  Flux<ArtistReferenceDTO> findMemberArtists(@Param("id") Long id);

  @Query("SELECT id, name FROM artist AS grp "
      + "LEFT JOIN artist_group ag ON grp.id = ag.group_id "
      + "WHERE ag.artist_id = :id")
  Flux<ArtistReferenceDTO> findGroupArtists(@Param("id") Long id);

  @Query("SELECT id, name FROM artist AS alias "
      + "LEFT JOIN artist_alias aa ON alias.id = aa.alias_id "
      + "WHERE aa.artist_id = :id" )
  Flux<ArtistReferenceDTO> findAliasArtists(@Param("id") Long id);

  @Query("SELECT url FROM artist_url WHERE artist_id = :id")
  Flux<String> findUrls(@Param("id") Long id);

  @Query("SELECT name_variation FROM artist_name_variation WHERE artist_id = :id")
  Flux<String> findNameVariations(@Param("id") Long id);
}
