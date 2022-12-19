package io.dsub.sweatboys.opendiscogs.api.label.application;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.infrastructure.LabelR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.label.infrastructure.LabelRepositoryImpl;
import io.dsub.sweatboys.opendiscogs.api.label.query.LabelQuery;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LabelServiceIntegrationTest extends AbstractDatabaseIntegrationTest {

    @Autowired
    LabelR2dbcRepository r2dbcRepository;
    @Autowired
    DatabaseClient databaseClient;
    @Autowired
    R2dbcEntityTemplate template;
    LabelService service;
    LabelRepository repository;
    List<Label> labels;

    @BeforeEach
    void setUp() {
        this.repository = new LabelRepositoryImpl(r2dbcRepository);
        this.service = new LabelService(repository);
        this.labels = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> TestUtil.getInstanceOf(Label.class)
                        .withId((long) i)
                        .withParentId(i % 2 == 0 ? (long) 1 : null)) // even num ids will have parent of id 1
                .peek(BaseEntity::setAsNew)
                .toList();
        r2dbcRepository.save(labels.get(0))
                .block(); // wait for transaction commit
        r2dbcRepository.saveAll(this.labels.stream()
                        .filter(l -> !Objects.equals(l.getId(), 1L))
                        .toList())
                .blockLast();
    }

    @AfterEach
    void cleanUp() {
        TestUtil.deleteAll(databaseClient);
    }

    @Test
    void findLabelsReturnsLabels() {
        var q = LabelQuery.builder().build();
        var p = PageRequest.of(0, 10);
        var page = service.findLabels(q, p).block();
        assertThat(page).isNotNull();
        assertThat(page.getItems()).hasSize(10);
    }

    @Test
    void findLabelsReturnsByExample() {
        var p = PageRequest.of(0, 5);
        for (Label label : labels) {
            var q = LabelQuery.builder()
                    .name(label.getName())
                    .build();
            var page = service.findLabels(q, p).block();
            assertNotNull(page);
            assertThat(page.getItems().contains(label));
        }
    }

    @Test
    void findLabelsWithSortMarksAsSorted() {
        var p = PageRequest.of(0, 5, Direction.ASC, "id");
        for (Label label : labels) {
            var q = LabelQuery.builder()
                    .name(label.getName())
                    .build();
            var response = service.findLabels(q, p).block();
            assertNotNull(response);
            assertThat(response.getSorted()).isNotNull().isTrue();
        }
        var response = service.findLabels(LabelQuery.builder().build(), p).block();
        assertNotNull(response);
        assertThat(response.getSorted()).isNotNull().isTrue();
        for (int i = 1; i <= 5; i++) {
            var item = response.getItems().get(i - 1);
            assertNotNull(item);
            var id = item.getId();
            assertNotNull(id);
            var intID = id.intValue();
            assertThat(intID).isEqualTo(i);
        }
    }

    @Test
    void getLabelReleasesReturnsAllReleases() {
        Flux.range(1, 2)
                .flatMap(i -> template.insert(Release.class)
                        .using(TestUtil.getInstanceOf(Release.class)
                                .withId((long) i)
                                .withMasterId(null)
                                .withReleasedYear(2022)
                                .withReleasedMonth(i)
                                .withReleasedDay(i)))
                .blockLast();
        Flux.range(1, 2)
                .flatMap(i -> template.insert(Artist.class)
                        .using(TestUtil.getInstanceOf(Artist.class)
                                .withId((long) i)))
                .blockLast();
        Flux.range(1, 2)
                .flatMap(i ->
                        databaseClient
                                .sql("INSERT INTO release_artist VALUES($1, $1)")
                                .bind(0, i)
                                .then())
                .blockLast();
        Flux.range(1, 2)
                .flatMap(i -> databaseClient
                        .sql("INSERT INTO release_format (release_id, format_hash, description) VALUES (:i,:i,'test-description')")
                        .bind("i", i)
                        .then())
                .blockLast();
        Flux.range(1, 2)
                .flatMap(i -> databaseClient
                        .sql("INSERT INTO label_release (label_id, release_id, category_notation) VALUES (:i,:i,'test-catno')")
                        .bind("i", i)
                        .then())
                .blockLast();
        for (long i : List.of(1L, 2L)) {
            var dto = service.getLabelReleases(i, Pageable.ofSize(2)).block();
            assertThat(dto).isNotNull();
            assertThat(dto.getItems()).isNotNull().isNotEmpty().hasSize(1);
            for (LabelReleaseDTO item : dto.getItems()) {
                assertThat(item.id()).isEqualTo(i);
                assertThat(item.name()).isNotNull().isNotBlank();
                assertThat(item.title()).isNotNull().isNotBlank();
                assertThat(item.releasedYear()).isEqualTo(2022);
                assertThat(item.status()).isNotNull().isNotBlank();
                assertThat(item.categoryNotation()).isNotNull().isEqualTo("test-catno");
                assertThat(item.description()).isNotNull().isEqualTo("test-description");
            }
        }
    }
}
