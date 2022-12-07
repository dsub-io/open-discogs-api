package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.core.entity.PersistableBaseEntity;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;

class ArtistRepositoryImplTest extends AbstractDatabaseIntegrationTest {
  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  ArtistRepository repository;
  List<Artist> artists;

  @BeforeEach
  void setUp() {
    repository = new ArtistRepositoryImpl(r2dbcRepository);
    var items = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> Artist.builder()
            .id((long) i)
            .name(TestUtil.getRandomString())
            .realName(TestUtil.getRandomString())
            .profile(TestUtil.getRandomString())
            .dataQuality(TestUtil.getRandomString())
            .build())
        .peek(PersistableBaseEntity::setAsNew)
        .toList();
    artists = r2dbcRepository.saveAll(items).collectList().block();
    assertThat(artists).hasSize(10);
  }

  @AfterEach
  void tearDown() {
    r2dbcRepository.deleteAll().subscribe();
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
}
