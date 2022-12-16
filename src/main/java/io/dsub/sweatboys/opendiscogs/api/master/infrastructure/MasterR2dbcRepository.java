package io.dsub.sweatboys.opendiscogs.api.master.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterVideoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            SELECT artist.id, artist.name FROM master_artist
            JOIN artist on artist_id = artist.id
            WHERE master_id = :id
            """)
    Flux<ArtistReferenceDTO> findMasterArtists(@Param("id") Long id);

    @Query("""
            SELECT id FROM release
            WHERE release.master_id = :id AND release.is_master
            """)
    Mono<Long> findMainReleaseId(@Param("id") Long id);

    @Query("""
            SELECT r.id, r.title,  string_agg(a.name, ',') AS artist, r.released_year AS year, array_to_string(array_agg(distinct a.id),',') AS artist_id
            FROM release r
                     JOIN release_artist ra on r.id = ra.release_id
                     JOIN artist a on a.id = ra.artist_id
            WHERE r.master_id = :id
            GROUP BY r.id
            ORDER BY :sort
            OFFSET :offset LIMIT :limit
                        """)
    Flux<MasterReleaseDTO> findReleasesByMasterId(
            @Param("id") Long id,
            @Param("sort") String sort,
            @Param("offset") Long offset,
            @Param("limit") int limit);

    @Query("""
            SELECT count(*) FROM release
            WHERE master_id = :id
            """)
    Mono<Long> countReleasesByMasterId(@Param("id") Long id);

    @Query("""
            SELECT url, description, title FROM master_video
            WHERE master_id = :id
            """)
    Flux<MasterVideoDTO> findMasterVideos(@Param("id") Long id);
}