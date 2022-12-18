package io.dsub.sweatboys.opendiscogs.api.artist.application;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArtistServiceIntegrationTest extends AbstractDatabaseIntegrationTest {
  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  @Autowired
  DatabaseClient databaseClient;
  @Autowired
  R2dbcEntityTemplate template;
  @Autowired
  DSLContext jooq;
  ArtistService service;
  ArtistRepository repository;
  List<Artist> artists;

  final List<String> tables =
      List.of(
          "release_artist",
          "release_credited_artist",
          "artist_url",
          "artist_alias",
          "artist_group",
          "artist_name_variation",
          "artist",
          "release");

  @BeforeEach
  void setUp() {
    this.repository = new ArtistRepositoryImpl(r2dbcRepository, jooq);
    this.service = new ArtistService(repository);
    this.artists = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(Artist.class).withId((long) i))
        .peek(BaseEntity::setAsNew)
        .toList();
    r2dbcRepository.saveAll(artists).blockLast();
  }
  @AfterEach
  void cleanUp() {
    TestUtil.deleteAll(databaseClient);
  }
  @Test
  void findArtistsReturnsArtists() {
    var q = ArtistQuery.builder().build();
    var p = PageRequest.of(0, 10);
    var page = service.findArtists(q, p).block();
    assertThat(page).isNotNull();
    assertThat(page.getItems()).hasSize(10);
  }

  @Test
  void findArtistsReturnsByExample() {
    var p = PageRequest.of(0, 5);
    for (Artist artist : artists) {
      var q = ArtistQuery.builder()
          .name(artist.getName())
          .build();
      var page = service.findArtists(q, p).block();
      assertNotNull(page);
      assertThat(page.getItems().contains(artist));
    }
  }

  @Test
  void findArtistsWithSortMarksAsSorted() {
    var p = PageRequest.of(0, 5, Direction.ASC, "id");
    for (Artist artist : artists) {
      var q = ArtistQuery.builder()
          .name(artist.getName())
          .build();
      var response = service.findArtists(q, p).block();
      assertNotNull(response);
      assertThat(response.getSorted()).isNotNull().isTrue();
    }
    var response = service.findArtists(ArtistQuery.builder().build(), p).block();
    assertNotNull(response);
    assertThat(response.getSorted()).isNotNull().isTrue();
    for (int i = 1; i <= 5; i++) {
      var item = response.getItems().get(i-1);
      assertNotNull(item);
      var id = item.getId();
      assertNotNull(id);
      var intID = id.intValue();
      assertThat(intID).isEqualTo(i);
    }
  }
  @Test
  void getArtistReturnAllAssociates() {
    databaseClient.sql("""
INSERT INTO artist_alias(artist_id, alias_id)
VALUES (1,2), (1,3), (2,3), (2,1), (3,1), (3,2), (1, 10)
""").then().block();
    databaseClient.sql("""
INSERT INTO artist_group(artist_id, group_id) 
VALUES (1,4), (1,5), (1,6), (7,1), (8,1), (9,1)
""").then().block();
    databaseClient.sql("""
INSERT INTO artist_url(artist_id, url_hash, url) 
VALUES (1,1,'test_url_1'), (1,2,'test_url_2'), (1,3,'test_url_3')
""").then().block();
    databaseClient.sql("""
INSERT INTO artist_name_variation(artist_id, name_variation_hash, name_variation) 
VALUES (1,1,'test_name_var_1'), (1,2,'test_name_var_2'), (1,3,'test_name_var_3'), (1,4, 'test_name_var_4') 
""").then().block();
    var resp = service.getArtist(1L).block();
    assertThat(resp).isNotNull();
    var artist = resp.getBody();
    assertThat(artist).isNotNull();
    for (List<ArtistReferenceDTO> references : List.of(artist.aliases(), artist.groups(), artist.members())) {
      assertThat(references)
          .isNotNull()
          .isNotEmpty()
          .allSatisfy(ref -> assertThat(ref.id()).isNotNull().isNotEqualTo(1L))
          .allSatisfy(ref -> assertThat(ref.name()).isNotNull().isNotEqualTo(artist.name()))
          .allSatisfy(ref -> assertThat(ref.getResourceURL()).isNotNull().contains(ref.id().toString()))
          .hasSize(3);
    }
    for (String nameVariation : artist.nameVariations()) {
      assertThat(nameVariation).isNotNull().isNotBlank();
    }
    for (String url : artist.urls()) {
      assertThat(url).isNotNull().isNotBlank();
    }
  }
  @Test
  void getArtistReleasesReturnsAllReleases() {
    Flux.range(1, 10)
        .flatMap(i -> template.insert(Release.class)
            .using(TestUtil.getInstanceOf(Release.class)
                .withId((long) i)
                .withReleasedYear(2022)
                .withReleasedMonth(i)
                .withReleasedDay(i)))
        .blockLast();
    Flux.range(1, 10)
        .flatMap(i ->
            databaseClient
                .sql("INSERT INTO release_artist VALUES($1, $2)")
                .bind(0, i)
                .bind(1, i)
                .then())
        .blockLast();
    Flux.range(1, 10)
        .flatMap( i -> databaseClient
            .sql("INSERT INTO release_credited_artist "
                + "VALUES ($1,$1,$2,$3)")
            .bind(0, i)
            .bind(0, i)
            .bind(1, i * 10)
            .bind(2, "role " + i)
            .then())
        .blockLast();

    for (long i = 1; i <= 10; i++) {
      var dto = service.getArtistReleases(i, Pageable.ofSize(2)).block();
      assertThat(dto).isNotNull();
      assertThat(dto.getItems()).isNotNull().isNotEmpty().hasSize(1);
      for (ArtistReleaseDTO item :dto.getItems()) {
        assertThat(item.getId()).isEqualTo(i);
        assertThat(item.getReleasedYear()).isEqualTo(2022);
        assertThat(item.getReleasedMonth()).isEqualTo(i);
        assertThat(item.getReleasedDay()).isEqualTo(i);
        assertThat(item.getNotes()).isNotBlank();
        assertThat(item.getCountry()).isNotBlank();
        assertThat(item.getDataQuality()).isNotBlank();
        assertThat(item.getIsMaster()).isNotNull();
        assertThat(item.getListedReleaseDate()).isNotBlank();
        assertThat(item.getTitle()).isNotBlank();
        assertThat(item.getRole()).contains("Main", "role " + i);
      }
    }
  }

}
