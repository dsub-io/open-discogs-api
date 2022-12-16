package io.dsub.sweatboys.opendiscogs.api.master.controller;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.config.PageableWebFluxConfiguration;
import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.application.MasterService;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;

import java.util.Collections;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Import(PageableWebFluxConfiguration.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MasterController.class)
public class MasterControllerTest {
    @MockBean
    MasterService masterService;
    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchReturnsMasters() {
        PagedResponseDTO<Master> response = PagedResponseDTO.<Master>builder()
                .first(true)
                .last(false)
                .totalElements(1L)
                .items(List.of(TestUtil.getInstanceOf(Master.class)))
                .pageNumber(1)
                .sorted(false)
                .build();

        var queryCaptor = ArgumentCaptor.forClass(MasterQuery.class);
        var pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        when(masterService.findMasters(queryCaptor.capture(), pageableCaptor.capture()))
                .thenReturn(Mono.just(response));

        var gotResponse = client.get()
                .uri("/masters?size=40&title=test&year=2003")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(response.getClass())
                .returnResult()
                .getResponseBody();

        var q = queryCaptor.getValue();
        var p = pageableCaptor.getValue();

        assertThat(gotResponse).isNotNull();
        assertThat(gotResponse.getItems()).isNotNull().hasSize(1);
        verify(masterService, times(1)).findMasters(any(), any());
        assertThat(q)
                .satisfies(query -> assertThat(query.getTitle()).isEqualTo("test"))
                .satisfies(query -> assertThat(query.getYear()).isEqualTo(2003));
        assertThat(p)
                .satisfies(page -> assertThat(page.getPageNumber()).isEqualTo(0))
                .satisfies(page -> assertThat(page.getPageSize()).isEqualTo(30));
    }

    @Test
    void getMasterReturnsMaster() {
        var master = TestUtil.getInstanceOf(Master.class);
        var dto = MasterDetailDTO.fromMaster(master);
        var idCaptor = ArgumentCaptor.forClass(Long.class);
        when(masterService.getById(idCaptor.capture())).thenReturn(dto);

        var got = client.get()
                .uri("/masters/" + master.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MasterDetailDTO.class)
                .returnResult()
                .getResponseBody();

        verify(masterService, times(1)).getById(any());
        assertThat(got).isNotNull().isEqualTo(dto.block());
        assertThat(idCaptor.getValue()).isEqualTo(master.getId());
    }

    @Test
    void getMasterReturnsError() {
        var idCaptor = ArgumentCaptor.forClass(Long.class);
        when(masterService.getById(idCaptor.capture()))
                .thenReturn(Mono.error(new ItemNotFoundException("master", 33L)));
        var got = client.get()
                .uri("/masters/33")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        verify(masterService, times(1)).getById(any());
        assertThat(idCaptor.getValue()).isEqualTo(33L);
        assertThat(got).contains("not found", "master", "33");
    }

    @Test
    void getMasterReleasesReturnsError() {
        when(masterService.getMasterSubReleases(any(), any()))
                .thenReturn(Mono.error(new ItemNotFoundException("master", 1L)));
        var err = client.get()
                .uri("/masters/1/releases")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        verify(masterService, times(1)).getMasterSubReleases(any(), any());
        assertThat(err).contains("not found", "master", "1");
    }

    @Test
    void getMasterReleasesReturnsItem() {
        var dto = PagedResponseDTO.<MasterReleaseDTO>builder().build();
        when(masterService.getMasterSubReleases(any(), any()))
                .thenReturn(Mono.just(dto));
        var res = client.get()
                .uri("/masters/1/releases")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PagedResponseDTO.class)
                .returnResult()
                .getResponseBody();
        verify(masterService, times(1)).getMasterSubReleases(any(), any());
        assertThat(res)
                .isNotNull()
                .satisfies(r -> assertThat(r.getPageNumber()).isEqualTo(dto.getPageNumber()))
                .satisfies(r -> assertThat(r.getItems()).isEqualTo(dto.getItems()))
                .satisfies(r -> assertThat(r.getTotalElements()).isEqualTo(dto.getTotalElements()));
    }
}