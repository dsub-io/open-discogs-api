package io.dsub.sweatboys.opendiscogs.api.label.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.infrastructure.LabelR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.label.infrastructure.LabelRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractConcurrentDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.PageRequest;
import reactor.test.StepVerifier;


import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class LabelServiceIntegrationTest extends AbstractConcurrentDatabaseIntegrationTest {

    @Autowired
    LabelR2dbcRepository r2dbcRepository;
    @Autowired
    DatabaseClient databaseClient;
    LabelService service;
    LabelRepository repository;
    List<Label> labels;

    @BeforeEach
    void setUp() {
        this.repository = new LabelRepositoryImpl(r2dbcRepository);
        this.service = new LabelService(repository);
        this.labels = IntStream.rangeClosed(1,10)
                .mapToObj(i -> TestUtil.getInstanceOf(Label.class).withId((long) i))
                .peek(BaseEntity::setAsNew)
                .toList();
        var  insertThenCount = Flux.fromIterable(labels)
                .flatMap(r2dbcRepository::save)
                .then(r2dbcRepository.count());
        StepVerifier.create(insertThenCount)
                .expectNext((long) labels.size())
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
        databaseClient.sql("DELETE FROM label_release WHERE true").then().block();
        databaseClient.sql("DELETE FROM label_url WHERE true").then().block();
        databaseClient.sql("DELETE FROM label WHERE true").then().block();
    }

    @Test
    void findLabelsReturnNothing() {
        r2dbcRepository.deleteAll().block();
        StepVerifier.create(service.findLabels(LabelQuery.builder().build(), PageRequest.of(0, 10)))
            .expectNextMatches(response ->
                    Objects.equals(response.isFirst(), true) &&
                        Objects.equals(response.isLast(), true) &&
                        Objects.equals(response.getItems().size(), 0)
                )
            .verifyComplete();
    }

    @Test
    void findLabelsReturnsLabels() {
        var q = LabelQuery.builder().build();
        var p = PageRequest.of(0, 5);
        var page = service.findLabels(q,p).block();
        assertThat(page).isNotNull();
    }

    @Test
    void findLabelsReturnsByExample() {
        var p = PageRequest.of(0, 5);
        for(Label label : labels) {
            var q = LabelQuery.builder()
                    .name(label.getName())
                    .build();
            var page = service.findLabels(q,p).block();
            assertNotNull(page);
        }
    }
}
