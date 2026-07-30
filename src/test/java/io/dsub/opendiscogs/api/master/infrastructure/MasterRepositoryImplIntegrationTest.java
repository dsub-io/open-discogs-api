package io.dsub.opendiscogs.api.master.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.opendiscogs.api.artist.domain.Artist;
import io.dsub.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.opendiscogs.api.master.domain.Master;
import io.dsub.opendiscogs.api.release.domain.Release;
import io.dsub.opendiscogs.api.release.infrastructure.ReleaseR2dbcRepository;
import io.dsub.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.opendiscogs.api.test.util.TestUtil;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.r2dbc.core.DatabaseClient;

class MasterRepositoryImplIntegrationTest extends AbstractDatabaseIntegrationTest {

  @Autowired
  MasterR2dbcRepository masterDelegate;

  @Autowired
  ReleaseR2dbcRepository releaseDelegate;

  @Autowired
  ArtistR2dbcRepository artistDelegate;

  @Autowired
  DatabaseClient databaseClient;

  MasterRepositoryImpl repository;

  @BeforeEach
  void setUp() {
    repository = new MasterRepositoryImpl(masterDelegate);
    seedMasterGraph();
  }

  @AfterEach
  void cleanUp() {
    TestUtil.deleteAll(databaseClient);
  }

  @Test
  void returnsMasterDetailAndItsReleasesFromThePublishedSchema() {
    var detail = repository.findById(1L).block();

    assertThat(detail).isNotNull();
    assertThat(detail.id()).isEqualTo(1L);
    assertThat(detail.mainRelease()).isEqualTo(1L);
    assertThat(detail.year()).isEqualTo(2026);
    assertThat(detail.genres()).containsExactly("Electronic");
    assertThat(detail.styles()).containsExactly("Ambient");
    assertThat(detail.artists()).singleElement()
        .satisfies(artist -> {
          assertThat(artist.id()).isEqualTo(1L);
          assertThat(artist.name()).isEqualTo("E2E Artist");
        });
    assertThat(detail.videos()).singleElement()
        .satisfies(video -> assertThat(video.url()).isEqualTo("https://example.test/video"));

    var releases = repository.findReleasesByMasterId(1L, PageRequest.of(0, 10))
        .collectList()
        .block();
    assertThat(releases).singleElement()
        .satisfies(release -> {
          assertThat(release.releaseId()).isEqualTo(1L);
          assertThat(release.title()).isEqualTo("Master Release");
          assertThat(release.artist()).containsExactly("E2E Artist");
          assertThat(release.artistId()).containsExactly(1L);
          assertThat(release.year()).isEqualTo(2026L);
        });
    assertThat(repository.countReleasesByMasterId(1L).block()).isEqualTo(1L);
  }

  private void seedMasterGraph() {
    var artist = Artist.builder()
        .id(1L)
        .name("E2E Artist")
        .dataQuality("Correct")
        .build();
    artist.setAsNew();
    artistDelegate.save(artist).block();

    var master = Master.builder()
        .id(1L)
        .title("E2E Master")
        .dataQuality("Correct")
        .releasedYear(2026)
        .build();
    master.setAsNew();
    masterDelegate.save(master).block();

    var release = Release.builder()
        .id(1L)
        .title("Master Release")
        .country("KR")
        .dataQuality("Correct")
        .releaseDate(LocalDate.of(2026, 7, 31))
        .hasValidYear(true)
        .hasValidMonth(true)
        .hasValidDay(true)
        .listedReleaseDate("2026-07-31")
        .isMaster(true)
        .masterId(1L)
        .status("Accepted")
        .build();
    release.setAsNew();
    releaseDelegate.save(release).block();

    databaseClient.sql("""
            UPDATE master SET main_release_id = 1 WHERE id = 1;
            INSERT INTO genre (name) VALUES ('Electronic');
            INSERT INTO style (name) VALUES ('Ambient');
            INSERT INTO master_genre
              (created_at, last_modified_at, genre, master_id)
            VALUES (NOW(), NOW(), 'Electronic', 1);
            INSERT INTO master_style
              (created_at, last_modified_at, style, master_id)
            VALUES (NOW(), NOW(), 'Ambient', 1);
            INSERT INTO master_artist
              (created_at, last_modified_at, artist_id, master_id)
            VALUES (NOW(), NOW(), 1, 1);
            INSERT INTO release_item_artist
              (created_at, last_modified_at, artist_id, release_item_id)
            VALUES (NOW(), NOW(), 1, 1);
            INSERT INTO master_video
              (created_at, last_modified_at, hash, description, title, url, master_id)
            VALUES
              (NOW(), NOW(), 1, 'description', 'video',
               'https://example.test/video', 1);
            """)
        .then()
        .block();
  }
}
