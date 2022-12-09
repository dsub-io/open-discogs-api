package io.dsub.sweatboys.opendiscogs.api.artist.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractConcurrentDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ArtistServiceIntegrationTest extends AbstractConcurrentDatabaseIntegrationTest {

  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  @Autowired
  DatabaseClient databaseClient;
  ArtistService service;
  ArtistRepository repository;
  List<Artist> artists;

  @BeforeEach
  void setUp() {
    this.repository = new ArtistRepositoryImpl(r2dbcRepository);
    this.service = new ArtistService(repository);
    this.artists = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(Artist.class).withId((long) i))
        .peek(BaseEntity::setAsNew)
        .toList();
    var insertThenCount = Flux.fromIterable(artists)
        .flatMap(r2dbcRepository::save)
        .then(r2dbcRepository.count());
    StepVerifier.create(insertThenCount)
        .expectNext((long) artists.size())
        .verifyComplete();
  }

  @AfterEach
  void tearDown() {
    databaseClient.sql("DELETE FROM artist_alias WHERE true").then().block();
    databaseClient.sql("DELETE FROM artist_group WHERE true").then().block();
    databaseClient.sql("DELETE FROM artist_url WHERE true").then().block();
    databaseClient.sql("DELETE FROM artist_name_variation WHERE true").then().block();
    databaseClient.sql("DELETE FROM artist WHERE true").then().block();
  }

  @Test
  void findArtistsReturnsNothing() {
    r2dbcRepository.deleteAll().block();
    StepVerifier.create(service.findArtists(ArtistQuery.builder().build(), PageRequest.of(0, 10)))
        .expectNextMatches(response ->
                Objects.equals(response.isFirst(), true) &&
                    Objects.equals(response.isLast(), true) &&
                    Objects.equals(response.isSorted(), false) &&
                    Objects.equals(response.getItems().size(), 0)
            )
        .verifyComplete();
  }

  @Test
  void findArtistsReturnsArtists() {
    var q = ArtistQuery.builder().build();
    var p = PageRequest.of(0, 5);
    var page = service.findArtists(q, p).block();
    assertThat(page).isNotNull();
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
      assertThat(response.isSorted()).isTrue();
    }
    var response = service.findArtists(ArtistQuery.builder().build(), p).block();
    assertNotNull(response);
    assertThat(response.isSorted()).isTrue();
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
  void MustReturnAllAssociates() {
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
    for (List<ArtistReferenceDTO> references : List.of(artist.getAliases(), artist.getGroups(), artist.getMembers())) {
      assertThat(references)
          .isNotNull()
          .isNotEmpty()
          .allSatisfy(ref -> assertThat(ref.id()).isNotNull().isNotEqualTo(1L))
          .allSatisfy(ref -> assertThat(ref.name()).isNotNull().isNotEqualTo(artist.getName()))
          .hasSize(3);
    }
    for (String nameVariation : artist.getNameVariations()) {
      assertThat(nameVariation).isNotNull().isNotBlank();
    }
    for (String url : artist.getUrls()) {
      assertThat(url).isNotNull().isNotBlank();
    }
  }
}
