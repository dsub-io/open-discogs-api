package io.dsub.opendiscogs.api.artist.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.opendiscogs.api.artist.domain.Artist;
import io.dsub.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.opendiscogs.api.test.util.TestUtil;
import java.util.List;
import java.util.stream.IntStream;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.r2dbc.core.DatabaseClient;

class ArtistRepositoryImplIntegrationTest extends AbstractDatabaseIntegrationTest {

  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  @Autowired
  DatabaseClient databaseClient;
  @Autowired
  DSLContext jooq;
  ArtistRepository repository;
  List<Artist> artists;

  @BeforeEach
  void setUp() {
    repository = new ArtistRepositoryImpl(r2dbcRepository, jooq);
    var items = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> Artist.builder()
            .id((long) i)
            .name(TestUtil.getRandomString())
            .realName(TestUtil.getRandomString())
            .profile(TestUtil.getRandomString())
            .dataQuality(TestUtil.getRandomString())
            .build())
        .peek(BaseEntity::setAsNew)
        .toList();
    artists = r2dbcRepository.saveAll(items).collectList().block();
    assertThat(artists).hasSize(10);
  }

  @AfterEach
  void tearDown() {
    TestUtil.deleteAll(databaseClient);
  }

  @Test
  void findAllByReturnsByName() {
    for (Artist artist : artists) {
      var example = Example.of(
          Artist.builder()
              .name(artist.getName())
              .build(),
          ExampleMatcher
              .matching()
              .withIgnoreNullValues()
              .withIgnoreCase()
              .withIgnorePaths("createdAt", "lastModifiedAt")
      );
      var pageable = PageRequest.of(0, 5);
      var result = repository.findAllBy(example, pageable).block();
      assertThat(result).isNotNull().isNotEmpty();
      assertThat(result.getTotalElements()).isEqualTo(1);
      assertThat(result.getTotalPages()).isEqualTo(1);
      assertThat(result.getNumberOfElements()).isEqualTo(1);
      assertThat(result.getContent()).isNotNull().isNotEmpty().hasSize(1);
    }
  }

  @Test
  void MustReturnAllAssociates() {
    databaseClient.sql("""
        INSERT INTO artist_alias(created_at, last_modified_at, artist_id, alias_id)
        VALUES
          (NOW(), NOW(), 1, 2), (NOW(), NOW(), 1, 3),
          (NOW(), NOW(), 2, 3), (NOW(), NOW(), 2, 1),
          (NOW(), NOW(), 3, 1), (NOW(), NOW(), 3, 2),
          (NOW(), NOW(), 1, 10)
        """).then().block();
    databaseClient.sql("""
        INSERT INTO artist_group(created_at, last_modified_at, artist_id, group_id)
        VALUES
          (NOW(), NOW(), 1, 4), (NOW(), NOW(), 1, 5), (NOW(), NOW(), 1, 6),
          (NOW(), NOW(), 7, 1), (NOW(), NOW(), 8, 1), (NOW(), NOW(), 9, 1)
        """).then().block();
    databaseClient.sql("""
        INSERT INTO artist_member(created_at, last_modified_at, artist_id, member_id)
        VALUES
          (NOW(), NOW(), 1, 7), (NOW(), NOW(), 1, 8), (NOW(), NOW(), 1, 9)
        """).then().block();
    databaseClient.sql("""
        INSERT INTO artist_url(created_at, last_modified_at, artist_id, hash, url)
        VALUES
          (NOW(), NOW(), 1, 1, 'test_url_1'),
          (NOW(), NOW(), 1, 2, 'test_url_2'),
          (NOW(), NOW(), 1, 3, 'test_url_3')
        """).then().block();
    databaseClient.sql("""
        INSERT INTO artist_name_variation
          (created_at, last_modified_at, artist_id, hash, name_variation)
        VALUES
          (NOW(), NOW(), 1, 1, 'test_name_var_1'),
          (NOW(), NOW(), 1, 2, 'test_name_var_2'),
          (NOW(), NOW(), 1, 3, 'test_name_var_3'),
          (NOW(), NOW(), 1, 4, 'test_name_var_4')
        """).then().block();
    var artist = repository.findById(1L).block();
    assertThat(artist).isNotNull();
    for (List<ArtistReferenceDTO> references : List.of(artist.aliases(), artist.groups(),
        artist.members())) {
      assertThat(references)
          .isNotNull()
          .isNotEmpty()
          .allSatisfy(ref -> assertThat(ref.id()).isNotNull().isNotEqualTo(1L))
          .allSatisfy(ref -> assertThat(ref.name()).isNotNull().isNotEqualTo(artist.name()))
          .hasSize(3);
    }
    for (String nameVariation : artist.nameVariations()) {
      assertThat(nameVariation).isNotNull().isNotBlank();
    }
    for (String url : artist.urls()) {
      assertThat(url).isNotNull().isNotBlank();
    }
  }
}
