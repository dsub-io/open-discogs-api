package io.dsub.sweatboys.opendiscogs.api.artist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DURATION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.entity.PersistableBaseEntity;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

class ArtistServiceTest extends AbstractDatabaseIntegrationTest {

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
  @Order(1)
  void findArtistsReturnsNothing() {
    r2dbcRepository.deleteAll().subscribe();
    StepVerifier.create(service.findArtists(ArtistQuery.builder().build(), PageRequest.of(0, 10)))
        .expectNextMatches(Streamable::isEmpty)
        .verifyComplete();
  }

  @Test
  @Order(2)
  void findArtistsReturnsArtists() {
    var q = ArtistQuery.builder().build();
    var p = PageRequest.of(0, 5);
    var page = service.findArtists(q, p).block();

    assertThat(page).isNotNull();
    assertThat(page.getTotalElements()).isEqualTo(10);
    assertThat(page.getTotalPages()).isEqualTo(2);
    assertThat(page.getNumber()).isEqualTo(0);
    assertThat(page.getSize()).isEqualTo(5);
    assertThat(page.getContent()).hasSize(5);
  }

  @Test
  @Order(3)
  void findArtistsReturnsByExample() {
    var p = PageRequest.of(0, 5);
    for (Artist artist : artists) {
      var q = ArtistQuery.builder()
          .name(artist.getName())
          .build();
      var page = service.findArtists(q, p).block();
      assertNotNull(page);
      var result = page.getContent();
      assertThat(result).isNotNull().isNotEmpty();
      for (Artist got : result) {
        assertThat(got.getName()).contains(artist.getName());
      }
      System.out.println("pass");
    }
  }
}