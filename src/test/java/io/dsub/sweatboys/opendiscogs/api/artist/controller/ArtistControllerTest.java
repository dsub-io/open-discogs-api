package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.dsub.sweatboys.opendiscogs.api.artist.application.ArtistService;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.config.PageableWebFluxConfiguration;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Import(PageableWebFluxConfiguration.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ArtistController.class)
class ArtistControllerTest extends ConcurrentTest {

  @MockBean
  ArtistService artistService;
  @MockBean
  ArtistRepository artistRepository;
  @Autowired
  private WebTestClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void searchArtistsReturns() {
    PagedResponseDTO<Artist> response = PagedResponseDTO.<Artist>builder()
        .first(true)
        .last(false)
        .totalElements(1)
        .items(List.of(TestUtil.getInstanceOf(Artist.class)))
        .pageNumber(1)
        .sorted(false)
        .build();

    var artistCaptor = ArgumentCaptor.forClass(ArtistQuery.class);
    var pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

    when(artistService.findArtists(artistCaptor.capture(), pageableCaptor.capture()))
        .thenReturn(Mono.just(response));

    var gotResponse = client.get()
        .uri("/artists?size=40")
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK)
        .expectBody(response.getClass())
        .returnResult()
        .getResponseBody();

    var query = artistCaptor.getValue();
    var pageable = pageableCaptor.getValue();

    assertThat(gotResponse).isNotNull();
    assertThat(gotResponse.getItems()).isNotNull().hasSize(1);
    verify(artistService, atMostOnce()).findArtists(any(), any());
    assertThat(query)
        .satisfies(q -> assertThat(q.name()).isNull())
        .satisfies(q -> assertThat(q.realName()).isNull())
        .satisfies(q -> assertThat(q.profile()).isNull());
    assertThat(pageable)
        .satisfies(p -> assertThat(p.getPageNumber()).isEqualTo(1))
        .satisfies(p -> assertThat(p.getPageSize()).isEqualTo(30));
  }

  @Test
  void getArtist() {
    var idCaptor = ArgumentCaptor.forClass(Long.class);
    var dto = TestUtil.getInstanceOf(ArtistDetailDTO.class).withId(1L);
    when(artistService
        .getArtist(idCaptor.capture()))
        .thenReturn(Mono.just(ResponseEntity.ok(dto)));
    var got = client.get()
        .uri("/artists/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ArtistDetailDTO.class)
        .returnResult()
        .getResponseBody();
    verify(artistService, atMostOnce()).getArtist(1L);
    assertThat(got).isNotNull();
    assertThat(got.getId()).isNotNull().isEqualTo(dto.getId());
    assertThat(got.getName()).isNotNull().isEqualTo(dto.getName());
    assertThat(got.getRealName()).isNotNull().isEqualTo(dto.getRealName());
    assertThat(got.getProfile()).isNotNull().isEqualTo(dto.getProfile());
  }
}