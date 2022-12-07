package io.dsub.sweatboys.opendiscogs.api.artist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.entity.PersistableBaseEntity;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ArtistServiceTest extends AbstractConcurrentDatabaseIntegrationTest {

  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  ArtistService service;
  ArtistRepository repository;

  List<Artist> artists;

  @BeforeEach
  void setUp() {
    this.repository = new ArtistRepositoryImpl(r2dbcRepository);
    this.service = new ArtistService(repository);
    this.artists = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(Artist.class).withId((long) i))
        .peek(PersistableBaseEntity::setAsNew)
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
    r2dbcRepository.deleteAll().subscribe();
  }

  @Test
  void findArtistsReturnsNothing() {
    r2dbcRepository.deleteAll().subscribe();
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
}
