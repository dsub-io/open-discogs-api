package io.dsub.sweatboys.opendiscogs.api.master.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterVideoDTO;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MasterRepositoryImplTest {

  @Mock
  MasterR2dbcRepository delegate;
  @InjectMocks
  MasterRepositoryImpl masterRepository;
  @Captor
  ArgumentCaptor<Example<Master>> exampleCaptor;
  @Captor
  ArgumentCaptor<Function<FluentQuery.ReactiveFluentQuery<Master>, Publisher<Object>>> queryFuncCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllByReturnsPage() {
    List<Master> masters = IntStream.rangeClosed(1, 10)
        .mapToObj(id -> TestUtil.getInstanceOf(Master.class).withId((long) id))
        .toList();
    Page<Master> page = new PageImpl<>(masters);

    given(delegate.findBy(exampleCaptor.capture(), queryFuncCaptor.capture()))
        .willReturn(Mono.just(page));

    Pageable pageable = PageRequest.ofSize(10);
    Example<Master> example = Example.of(Master.builder().build());

    StepVerifier.create(masterRepository.findAllBy(example, pageable))
        .expectNext(page)
        .verifyComplete();

    verify(delegate, times(1)).findBy(any(), any());
    assertThat(exampleCaptor.getValue()).isEqualTo(example);
    assertThat(queryFuncCaptor.getValue()).isNotNull();
  }

  @Test
  void findByIdReturnsMaster() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var master = TestUtil.getInstanceOf(Master.class);
    var genres = IntStream.rangeClosed(0, 5).mapToObj(i -> TestUtil.getRandomString()).toList();
    var styles = IntStream.rangeClosed(0, 5).mapToObj(i -> TestUtil.getRandomString()).toList();
    var artists = IntStream.rangeClosed(0, 3).mapToObj(i -> TestUtil.getInstanceOf(ArtistReferenceDTO.class)).toList();
    var videos = IntStream.rangeClosed(0, 8).mapToObj(i -> TestUtil.getInstanceOf(MasterVideoDTO.class)).toList();
    var releaseID = TestUtil.RANDOM.nextLong();

    given(delegate.findById(idCaptor.capture())).willReturn(Mono.just(master));
    given(delegate.findMainReleaseId(idCaptor.capture())).willReturn(Mono.just(releaseID));
    given(delegate.findMasterGenres(idCaptor.capture())).willReturn(Flux.fromIterable(genres));
    given(delegate.findMasterStyles(idCaptor.capture())).willReturn(Flux.fromIterable(styles));
    given(delegate.findMasterArtists(idCaptor.capture())).willReturn(Flux.fromIterable(artists));
    given(delegate.findMasterVideos(idCaptor.capture())).willReturn(Flux.fromIterable(videos));

    StepVerifier.create(masterRepository.findById(master.getId()))
        .assertNext(dto -> assertThat(dto)
            .isNotNull()
            .satisfies(d -> assertThat(d.mainRelease()).isEqualTo(releaseID))
            .satisfies(d -> assertThat(d.id()).isEqualTo(master.getId()))
            .satisfies(d -> assertThat(d.title()).isEqualTo(master.getTitle()))
            .satisfies(d -> assertThat(d.dataQuality()).isEqualTo(master.getDataQuality()))
            .satisfies(d -> assertThat(d.genres()).isEqualTo(genres))
            .satisfies(d -> assertThat(d.styles()).isEqualTo(styles))
            .satisfies(d -> assertThat(d.artists()).isEqualTo(artists))
            .satisfies(d -> assertThat(d.videos()).isEqualTo(videos)))
        .verifyComplete();

    assertThat(idCaptor.getAllValues())
        .hasSize(6)
        .allSatisfy(id -> assertThat(id).isEqualTo(master.getId()));

    verify(delegate, times(1)).findMasterArtists(any());
    verify(delegate, times(1)).findMasterGenres(any());
    verify(delegate, times(1)).findMasterStyles(any());
    verify(delegate, times(1)).findMasterVideos(any());
    verify(delegate, times(1)).findById(master.getId());
  }

  @Test
  void findReleasesByMasterId() {
    var releases =IntStream.rangeClosed(1, 10)
            .mapToObj(i -> TestUtil.getInstanceOf(MasterReleaseDTO.class))
            .toList();
    var p = PageRequest.of(0, 10, Sort.by(Sort.Order.by("id")));
    var id = 1L;
    given(delegate.findReleasesByMasterId(id, "id ASC", 0L, 10))
            .willReturn(Flux.fromIterable(releases));
    StepVerifier.create(masterRepository.findReleasesByMasterId(1L, p))
            .expectNextSequence(releases)
            .verifyComplete();
    verify(delegate, times(1))
            .findReleasesByMasterId(id, "id ASC", 0L, 10);
  }

  @Test
  void countReleasesByMasterId() {
    given(delegate.countReleasesByMasterId(1L))
            .willReturn(Mono.just(10L));
    StepVerifier.create(masterRepository.countReleasesByMasterId(1L))
            .expectNext(10L)
            .verifyComplete();
    verify(delegate, times(1))
            .countReleasesByMasterId(1L);
  }

}