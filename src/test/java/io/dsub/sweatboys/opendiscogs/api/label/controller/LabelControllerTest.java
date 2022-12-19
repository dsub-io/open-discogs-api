package io.dsub.sweatboys.opendiscogs.api.label.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.dsub.sweatboys.opendiscogs.api.config.PageableWebFluxConfiguration;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.application.LabelService;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Import(PageableWebFluxConfiguration.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = LabelController.class)
class LabelControllerTest extends ConcurrentTest {

  @MockBean
  LabelService labelService;
  @MockBean
  LabelRepository labelRepository;
  @Autowired
  private WebTestClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void searchLabelsReturns() {
    PagedResponseDTO<Label> response = PagedResponseDTO.<Label>builder()
        .first(true)
        .last(false)
        .totalElements(1L)
        .items(List.of(TestUtil.getInstanceOf(Label.class)))
        .pageNumber(1)
        .sorted(false)
        .build();

    var labelCaptor = ArgumentCaptor.forClass(LabelQuery.class);
    var pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

    when(labelService.findLabels(labelCaptor.capture(), pageableCaptor.capture()))
        .thenReturn(Mono.just(response));

    var gotResponse = client.get()
        .uri("/labels?size=30")
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK)
        .expectBody(response.getClass())
        .returnResult()
        .getResponseBody();

    var query = labelCaptor.getValue();
    var pageable = pageableCaptor.getValue();

    assertThat(gotResponse).isNotNull();
    assertThat(gotResponse.getItems()).isNotNull().hasSize(1);
    verify(labelService, atMostOnce()).findLabels(any(), any());
    assertThat(query)
        .satisfies(q -> assertThat(q.contactInfo()).isNull())
        .satisfies(q -> assertThat(q.dataQuality()).isNull())
        .satisfies(q -> assertThat(q.name()).isNull())
        .satisfies(q -> assertThat(q.profile()).isNull());
    assertThat(pageable)
        .satisfies(p -> assertThat(p.getPageNumber()).isEqualTo(0))
        .satisfies(p -> assertThat(p.getPageSize()).isEqualTo(30));
  }

  @Test
  void getLabelReturns() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var dto = TestUtil.getInstanceOf(LabelDetailDTO.class).withId(1L);
    when(labelService
        .getLabel(idCaptor.capture()))
        .thenReturn(Mono.just(ResponseEntity.ok(dto)));
    var got = client.get()
        .uri("/labels/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(LabelDetailDTO.class)
        .returnResult()
        .getResponseBody();
    verify(labelService, atMostOnce()).getLabel(1L);
    assertThat(got).isNotNull();
    assertThat(got.id()).isNotNull().isEqualTo(dto.id());
    assertThat(got.contactInfo()).isNotNull().isEqualTo(dto.contactInfo());
    assertThat(got.dataQuality()).isNotNull().isEqualTo(dto.dataQuality());
    assertThat(got.name()).isNotNull().isEqualTo(dto.name());
    assertThat(got.profile()).isNotNull().isEqualTo(dto.profile());
  }

  @Test
  void getLabelReleasesReturns() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    var labelRelease = TestUtil.getInstanceOf(LabelReleaseDTO.class);
    var page = new PageImpl<>(List.of(labelRelease), PageRequest.of(1, 10), 1);

    when(labelService.getLabelReleases(idCaptor.capture(), pageableCaptor.capture()))
        .thenReturn(PagedResponseDTO.fromPage(page));

    var result = client.get()
        .uri("/labels/18/releases")
        .exchange()
        .expectBody(PagedResponseDTO.class)
        .returnResult();

    var body = result.getResponseBody();
    assertThat(body).isNotNull();
    var items = body.getItems();
    assertThat(items).isNotNull().isNotEmpty().hasSize(1);
  }


}
