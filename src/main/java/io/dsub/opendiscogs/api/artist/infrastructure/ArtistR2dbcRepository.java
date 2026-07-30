package io.dsub.opendiscogs.api.artist.infrastructure;

import io.dsub.opendiscogs.api.artist.domain.Artist;
import io.dsub.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtistR2dbcRepository extends R2dbcRepository<Artist, Long> {

  @Query("""
      SELECT artist.id, artist.name
      FROM artist_member
      JOIN artist ON artist.id = artist_member.member_id
      WHERE artist_member.artist_id = :id
      """)
  Flux<ArtistReferenceDTO> findMemberArtists(@Param("id") Long id);

  @Query("""
      SELECT artist.id, artist.name
      FROM artist_group
      JOIN artist ON artist.id = artist_group.group_id
      WHERE artist_group.artist_id = :id
      """)
  Flux<ArtistReferenceDTO> findGroupArtists(@Param("id") Long id);

  @Query("""
      SELECT artist.id, artist.name
      FROM artist_alias
      JOIN artist ON artist.id = artist_alias.alias_id
      WHERE artist_alias.artist_id = :id
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
      SELECT (
      (SELECT COUNT(*)
      FROM release_item_artist AS ra
      LEFT JOIN release_item ON release_item.id = ra.release_item_id
      WHERE artist_id = :id)
      +
      (SELECT COUNT(*)
      FROM release_item_credited_artist AS rca
      LEFT JOIN release_item ON release_item.id = rca.release_item_id
      WHERE artist_id = :id)
      );
      """)
  Mono<Long> countReleasesByArtistId(@Param("id") Long id);
}
