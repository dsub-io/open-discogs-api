package io.dsub.sweatboys.opendiscogs.api.label.application;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class LabelServiceTest extends ConcurrentTest {

    @InjectMocks
    LabelService service;
    @Mock
    LabelRepository repository;

    @Captor
    ArgumentCaptor<Example<Label>> labelExampleArgumentCaptor;
    @Captor
    ArgumentCaptor<Pageable> pageableArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findLabels() {
        var exampleCaptor = labelExampleArgumentCaptor;
        var pageableCaptor = pageableArgumentCaptor;
        var labels = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> TestUtil.getInstanceOf(Label.class).withId((long) i))
                .toList();
        var query = TestUtil.getInstanceOf(LabelQuery.class);
        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name", "contact_info");

        Mono<Page<Label>> expected = Mono.fromCallable(
                        () -> (Page<Label>) new PageImpl<>(labels, pageableCaptor.getValue(), labels.size()))
                .subscribeOn(Schedulers.boundedElastic());

        given(repository.findAllBy(exampleCaptor.capture(), pageableCaptor.capture()))
                .willReturn(expected);

        StepVerifier.create(service.findLabels(query, pageable))
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
                .satisfies(ex -> assertThat(ex.getProbe().getContactInfo()).isEqualTo(query.contactInfo()))
                .satisfies(ex -> assertThat(ex.getProbe().getDataQuality()).isEqualTo(query.dataQuality()))
                .satisfies(ex -> assertThat(ex.getProbe().getProfile()).isEqualTo(query.profile()));
        assertThat(pageableCaptor.getValue()).isEqualTo(pageable);
    }

    @Test
    void getLabelReturnsLabel() {
        var dto = TestUtil.getInstanceOf(LabelDetailDTO.class);
        given(repository.findById(dto.id())).willReturn(Mono.just(dto));
        StepVerifier.create(service.getLabel(dto.id()))
                .assertNext(resp ->assertThat(resp)
                        .isNotNull()
                        .satisfies(r -> assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK))
                        .satisfies(r -> assertThat(r.getBody()).isEqualTo(dto)))
                .verifyComplete();
        verify(repository, atMostOnce()).findById(dto.id());
    }

    @Test
    void getLabelReturnsNotFoundError() {
        given(repository.findById(any())).willReturn(Mono.empty());
        StepVerifier.create(service.getLabel(1L))
                .expectErrorSatisfies(err -> assertThat(err)
                        .isNotNull()
                        .isInstanceOf(ItemNotFoundException.class)
                        .satisfies(e -> assertThat(((ItemNotFoundException) e).getStatusCode()).isEqualTo(
                                HttpStatus.NOT_FOUND)))
                .verify();
        verify(repository, atMostOnce()).findById(1L);
    }

    @Test
    void getLabelReleases() {
        var dtoList = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> TestUtil.getInstanceOf(LabelReleaseDTO.class).withId((long) i))
                .toList();
        var idCaptor = ArgumentCaptor.forClass(Long.class);
        var pageableCaptor = pageableArgumentCaptor;

        given(repository.findReleasesByLabelId(idCaptor.capture(), pageableCaptor.capture()))
                .willReturn(Flux.fromStream(dtoList.stream()));
        given(repository.countReleasesByLabelId(10L))
                .willReturn(Mono.just(10L));

        StepVerifier.create(service.getLabelReleases(10L, PageRequest.of(0, 10)))
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
                .countReleasesByLabelId(10L);
        verify(repository, times(1))
                .findReleasesByLabelId(any(),any());
        assertThat(idCaptor.getAllValues())
                .hasSize(1)
                .allSatisfy(id -> assertThat(id).isEqualTo(10L));
        assertThat(pageableCaptor.getAllValues())
                .hasSize(1);

    }
}
