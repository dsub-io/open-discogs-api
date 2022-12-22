package io.dsub.sweatboys.opendiscogs.api.release.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.dsub.sweatboys.opendiscogs.api.config.PageableWebFluxConfiguration;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.application.ReleaseService;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.query.ReleaseQuery;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Import(PageableWebFluxConfiguration.class)
//@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReleaseController.class)
class ReleaseControllerTest {

  @MockBean
  ReleaseService service;

  @Autowired
  WebTestClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void search() {
    var qCaptor = ArgumentCaptor.forClass(ReleaseQuery.class);
    var pCaptor = ArgumentCaptor.forClass(Pageable.class);
    var dtoList = LongStream.rangeClosed(1, 20)
        .mapToObj(id -> TestUtil.getInstanceOf(ReleaseDTO.class).withId(id))
        .toList();
    var dtoMono = PagedResponseDTO.fromPage(new PageImpl<>(dtoList, PageRequest.of(1, 20), 30));
    given(service.search(qCaptor.capture(), pCaptor.capture())).willReturn(dtoMono);
    var expected = dtoMono.block();
    assertThat(expected).isNotNull();
    var dto = client.get()
        .uri("/releases?title=test_title&country=test_country&year=2002&month=3")
        .exchange()
        .expectStatus().isOk()
        .expectBody(PagedResponseDTO.class)
        .returnResult()
        .getResponseBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getTotalElements()).isEqualTo(expected.getTotalElements());
    assertThat(dto.getItems().size()).isEqualTo(expected.getItems().size());
    assertThat(dto.getTotalPages()).isEqualTo(expected.getTotalPages());
    assertThat(qCaptor.getValue().title()).isEqualTo("test_title");
    assertThat(qCaptor.getValue().country()).isEqualTo("test_country");
    assertThat(qCaptor.getValue().isMaster()).isNull();
    assertThat(qCaptor.getValue().year()).isEqualTo(2002);
    assertThat(qCaptor.getValue().month()).isEqualTo(3);
  }

  @Test
  void getByIdReturnsRelease() {
    var dto = TestUtil.getInstanceOf(ReleaseDetailDTO.class);
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    given(service.getReleaseById(idCaptor.capture()))
        .willReturn(Mono.just(dto));
    var result = client.get()
        .uri("/releases/33")
        .exchange()
        .expectBody(ReleaseDetailDTO.class)
        .returnResult()
        .getResponseBody();
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(dto.getId());
    verify(service, times(1)).getReleaseById(any());
    assertThat(idCaptor.getValue()).isEqualTo(33L);
  }
}
