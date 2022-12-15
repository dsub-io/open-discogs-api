package io.dsub.sweatboys.opendiscogs.api.master.application;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.domain.MasterRepository;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.stream.IntStream;
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
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MasterServiceTest {

  @Mock
  MasterRepository masterRepository;
  @InjectMocks
  MasterService service;
  @Captor
  ArgumentCaptor<Example<Master>> exampleCaptor;
  @Captor
  ArgumentCaptor<Pageable> pageableCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findMastersReturnsValidPage() {
    var q = new MasterQuery(2013, "test");
    var p = PageRequest.ofSize(10);
    Page<Master> pageResult = new PageImpl<>(IntStream.rangeClosed(1, 10)
        .mapToObj(id -> TestUtil.getInstanceOf(Master.class).withId((long) id))
        .toList());
    given(masterRepository.findAllBy(exampleCaptor.capture(), pageableCaptor.capture()))
        .willReturn(Mono.just(pageResult));
    StepVerifier.create(service.findMasters(q, p))
        .assertNext(dto -> assertThat(dto).isNotNull()
            .satisfies(d -> assertThat(d.getItems()).isEqualTo(pageResult.getContent()))
            .satisfies(d -> assertThat(d.getPageNumber()).isEqualTo(1))
            .satisfies(d -> assertThat(d.getFirst()).isNotNull().isTrue())
            .satisfies(d -> assertThat(d.getLast()).isNotNull().isTrue())
            .satisfies(d -> assertThat(d.getPageSize()).isEqualTo(pageResult.getSize())))
        .verifyComplete();
    verify(masterRepository, times(1)).findAllBy(any(), any());
    assertThat(exampleCaptor.getValue().getProbe())
        .isNotNull()
        .satisfies(m -> assertThat(m.getTitle()).isEqualTo(q.title()))
        .satisfies(m -> assertThat(m.getReleasedYear()).isEqualTo(q.year()));
    assertThat(pageableCaptor.getValue()).isEqualTo(p);
  }

  @Test
  void getByIdReturnsMaster() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var dto = mock(MasterDetailDTO.class);
    given(masterRepository.findById(idCaptor.capture()))
        .willReturn(Mono.just(dto));
    StepVerifier.create(service.getById(1L))
        .expectNext(dto)
        .verifyComplete();
    assertThat(idCaptor.getValue()).isEqualTo(1L);
    assertThat(idCaptor.getAllValues()).hasSize(1);
    verify(masterRepository, times(1)).findById(any());
  }

  @Test
  void getByIdReturnsError() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    given(masterRepository.findById(idCaptor.capture())).willReturn(Mono.empty());
    StepVerifier.create(service.getById(1L))
        .expectErrorSatisfies(error -> assertThat(error)
            .isInstanceOf(ItemNotFoundException.class)
            .satisfies(err -> assertThat(((ItemNotFoundException)err).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND))
            .satisfies(err -> assertThat(((ItemNotFoundException)err).getReason())
                .contains("not found")
                .contains("1")))
        .verify();
    assertThat(idCaptor.getValue()).isEqualTo(1L);
    assertThat(idCaptor.getAllValues()).hasSize(1);
    verify(masterRepository, times(1)).findById(any());
  }
}