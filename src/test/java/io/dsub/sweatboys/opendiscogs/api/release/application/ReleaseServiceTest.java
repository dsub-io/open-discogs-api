package io.dsub.sweatboys.opendiscogs.api.release.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.release.domain.ReleaseRepository;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReleaseServiceTest {

  @Mock
  ReleaseRepository releaseRepository;
  @InjectMocks
  ReleaseService service;

  @Captor
  ArgumentCaptor<Example<Release>> exampleCaptor;
  @Captor
  ArgumentCaptor<Pageable> pageCaptor;

  @Captor
  ArgumentCaptor<Long> idCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void search() {
    var dtoList = LongStream.rangeClosed(1, 20)
        .mapToObj(id -> TestUtil.getInstanceOf(ReleaseDTO.class).withId(id))
        .toList();

    var pageRequest = PageRequest.of(0, 20);
    Page<ReleaseDTO> page = new PageImpl<>(dtoList, pageRequest, 30);

    given(releaseRepository.findAllBy(exampleCaptor.capture(), pageCaptor.capture()))
        .willReturn(Mono.just(page));

    var query = ReleaseQuery.builder()
        .title("test_title")
        .isMaster(true)
        .month(3)
        .year(2013)
        .country("test_country")
        .build();

    var pagedResult = service.search(query, pageRequest).block();
    assertThat(pagedResult).isNotNull();
    assertThat(pagedResult.getItems()).isEqualTo(dtoList);
    assertThat(pagedResult.getPageNumber()).isEqualTo(1);
    assertThat(pagedResult.getLast()).isNotNull().isNotNull().isFalse();
    assertThat(pagedResult.getTotalElements()).isEqualTo(30);
    assertThat(exampleCaptor.getAllValues()).hasSize(1);
    var example = exampleCaptor.getValue();
    assertThat(example).isNotNull();
    assertThat(example.getProbe()).isNotEqualTo(query.toRelease());

    assertThat(pageCaptor.getAllValues()).hasSize(1);
    assertThat(pageCaptor.getValue()).isEqualTo(pageRequest);
    verify(releaseRepository, times(1)).findAllBy(any(), any());
  }

  @Test
  void getReleaseByIdReturnsRelease() {
    var expected = TestUtil.getInstanceOf(ReleaseDetailDTO.class);
    given(releaseRepository.getById(idCaptor.capture()))
        .willReturn(Mono.just(expected));
    StepVerifier.create(service.getReleaseById(1L))
        .expectNext(expected)
        .verifyComplete();
    assertThat(idCaptor.getValue()).isEqualTo(1L);
    verify(releaseRepository, times(1)).getById(any());
  }

  @Test
  void getReleaseByIdReturnsError() {
    given(releaseRepository.getById(idCaptor.capture()))
        .willReturn(Mono.empty());
    StepVerifier.create(service.getReleaseById(1L))
        .expectError(ItemNotFoundException.class)
        .verify();
    assertThat(idCaptor.getValue()).isEqualTo(1L);
    verify(releaseRepository, times(1)).getById(any());
  }
}
