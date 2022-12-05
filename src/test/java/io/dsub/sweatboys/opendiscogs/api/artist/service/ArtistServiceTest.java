package io.dsub.sweatboys.opendiscogs.api.artist.service;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import reactor.test.StepVerifier;

class ArtistServiceTest extends AbstractDatabaseIntegrationTest {
  @Autowired
  ArtistR2dbcRepository r2dbcRepository;
  ArtistService service;
  ArtistRepository repository;

  @BeforeEach
  void setUp() {
    this.repository = new ArtistRepositoryImpl(r2dbcRepository);
    this.service = new ArtistService(repository);
  }

  @Test
  @Order(1)
  void findArtists() {
    StepVerifier.create(service.findArtists(PageRequest.of(0, 10)))
        .expectNextMatches(Streamable::isEmpty)
        .verifyComplete();
  }
}