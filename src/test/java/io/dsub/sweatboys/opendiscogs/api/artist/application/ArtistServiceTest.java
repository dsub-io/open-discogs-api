package io.dsub.sweatboys.opendiscogs.api.artist.application;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ArtistServiceTest extends ConcurrentTest {

  @InjectMocks
  ArtistService service;
  @Mock
  ArtistRepository repository;

  @Captor
  ArgumentCaptor<Example<Artist>> artistExampleArgumentCaptor;
  @Captor
  ArgumentCaptor<Pageable> pageableArgumentCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findArtists() {
    var exampleCaptor = artistExampleArgumentCaptor;
    var pageableCaptor = pageableArgumentCaptor;
    var artists = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(Artist.class).withId((long) i))
        .toList();
    var query = TestUtil.getInstanceOf(ArtistQuery.class);
    var pageable = PageRequest.of(0, 10, Direction.ASC, "name", "real_name");

    Mono<Page<Artist>> expected = Mono
        .fromCallable(
            () -> (Page<Artist>) new PageImpl<>(artists, pageableCaptor.getValue(), artists.size()))
        .subscribeOn(Schedulers.boundedElastic());

    given(repository.findAllBy(exampleCaptor.capture(), pageableCaptor.capture()))
        .willReturn(expected);

    StepVerifier.create(service.findArtists(query, pageable))
        .assertNext(dto -> assertThat(dto)
            .isNotNull()
            .satisfies(d -> assertThat(d.getItems()).hasSize(10))
            .satisfies(d -> assertThat(d.getSorted()).isNotNull().isTrue())
            .satisfies(d -> assertThat(d.getTotalElements()).isEqualTo(10L)))
        .verifyComplete();

    verify(repository, times(1)).findAllBy(any(), any());
    assertThat(exampleCaptor.getValue())
        .satisfies(ex -> assertThat(ex.getProbe()).isNotNull())
        .satisfies(ex -> assertThat(ex.getProbe().getName()).isEqualTo(query.name()))
        .satisfies(ex -> assertThat(ex.getProbe().getRealName()).isEqualTo(query.realName()))
        .satisfies(ex -> assertThat(ex.getProbe().getProfile()).isEqualTo(query.profile()));
    assertThat(pageableCaptor.getValue()).isEqualTo(pageable);
  }

  @Test
  void getArtistReturnsArtist() {
    var dto = TestUtil.getInstanceOf(ArtistDetailDTO.class);
    given(repository.findById(dto.id())).willReturn(Mono.just(dto));
    StepVerifier.create(service.getArtist(dto.id()))
        .assertNext(resp -> assertThat(resp)
            .isNotNull()
            .satisfies(r -> assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK))
            .satisfies(r -> assertThat(r.getBody()).isEqualTo(dto)))
        .verifyComplete();
    verify(repository, atMostOnce()).findById(dto.id());
  }

  @Test
  void getArtistReturnsNotFoundError() {
    given(repository.findById(any())).willReturn(Mono.empty());
    StepVerifier.create(service.getArtist(1L))
        .expectErrorSatisfies(err -> assertThat(err)
            .isNotNull()
            .isInstanceOf(ItemNotFoundException.class)
            .satisfies(e -> assertThat(((ItemNotFoundException) e).getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND)))
        .verify();
    verify(repository, atMostOnce()).findById(1L);
  }

  @Test
  void getArtistReleases() {
    var dtoList = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(ArtistReleaseDTO.class).withId((long) i))
        .toList();

    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var pageableCaptor = pageableArgumentCaptor;

    given(repository.findReleasesByArtistId(idCaptor.capture(), pageableCaptor.capture()))
        .willReturn(Flux.fromStream(dtoList.stream()));
    given(repository.countReleasesByArtistId(10L))
        .willReturn(Mono.just(10L));

    StepVerifier.create(service.getArtistReleases(10L, PageRequest.of(0, 10)))
        .assertNext(page -> assertThat(page)
            .isNotNull()
            .satisfies(p -> assertThat(p.getTotalElements())
                .isEqualTo(10L))
            .satisfies(p -> assertThat(p.getItems())
                .isNotNull()
                .hasSize(10)
                .allSatisfy(dto -> assertThat(dto)
                    .hasNoNullFieldsOrProperties())
        .containsAll(dtoList)))
        .verifyComplete();
    verify(repository, times(1))
        .countReleasesByArtistId(10L);
    verify(repository, times(1))
        .findReleasesByArtistId(any(), any());
    assertThat(idCaptor.getAllValues())
        .hasSize(1)
        .allSatisfy(id -> assertThat(id).isEqualTo(10L));
    assertThat(pageableCaptor.getAllValues())
        .hasSize(1);
  }
}