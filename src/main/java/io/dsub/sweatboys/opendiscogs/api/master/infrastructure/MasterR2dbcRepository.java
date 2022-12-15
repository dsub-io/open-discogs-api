package io.dsub.sweatboys.opendiscogs.api.master.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface MasterR2dbcRepository extends R2dbcRepository<Master, Long> {

  @Query("""
      SELECT name FROM master_style
      JOIN style on style_id = style.id
      WHERE master_id = :id
      """)
  Flux<String> findMasterStyles(@Param("id") Long id);

  @Query("""
      SELECT name FROM master_genre
      JOIN genre on genre_id = genre.id
      WHERE master_id = :id
      """)
  Flux<String> findMasterGenres(@Param("id") Long id);

  @Query("""
      SELECT id, name FROM master_artist
      JOIN artist on artist_id = artist.id
      WHERE master_id = :id
      """)
  Flux<ArtistReferenceDTO> findMasterArtists(@Param("id") Long id);
}