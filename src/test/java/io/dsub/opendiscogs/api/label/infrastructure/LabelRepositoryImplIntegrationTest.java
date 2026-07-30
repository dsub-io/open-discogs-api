package io.dsub.opendiscogs.api.label.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.opendiscogs.api.label.domain.Label;
import io.dsub.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.opendiscogs.api.test.util.TestUtil;
import java.util.List;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class LabelRepositoryImplIntegrationTest extends AbstractDatabaseIntegrationTest {

  @Autowired
  LabelR2dbcRepository r2dbcRepository;
  @Autowired
  DatabaseClient databaseClient;
  LabelRepository repository;
  List<Label> labels;

  @BeforeEach
  void setUp() {
    this.repository = new LabelRepositoryImpl(r2dbcRepository);
    var items = IntStream.rangeClosed(1, 10)
        .mapToObj(i -> TestUtil.getInstanceOf(Label.class)
            .withId((long) i))
        .peek(BaseEntity::setAsNew)
        .toList();
    labels = r2dbcRepository.saveAll(items).collectList().block();
    assertThat(labels).hasSize(10);
  }

  @AfterEach
  void tearDown() {
    TestUtil.deleteAll(databaseClient);
  }

  @Test
  void findAllByReturnsByNameAndProfile() {
    for (Label label : labels) {
      var example = Example.of(
          Label.builder()
              .name(label.getName())
              .profile(label.getProfile())
              .build(),
          ExampleMatcher
              .matching()
              .withIgnoreNullValues()
              .withIgnoreCase()
              .withIgnorePaths("createdAt", "lastModifiedAt")
      );
      var pageable = PageRequest.of(0, 5);
      var result = repository.findAllBy(example, pageable).block();
      assertThat(result).isNotNull().isNotEmpty();
      assertThat(result.getTotalElements()).isEqualTo(1);
      assertThat(result.getTotalPages()).isEqualTo(1);
      assertThat(result.getNumberOfElements()).isEqualTo(1);
      assertThat(result.getContent()).isNotNull().isNotEmpty().hasSize(1);
    }
  }

  @Test
  void MustReturnsAllAssociates() {
    // parent = id - 1
    Flux.range(1, 10)
        .flatMap(this::fillParentColumn)
        .zipWith(Flux.range(1, 10).flatMap(i -> Mono.just(TestUtil.getRandomString())))
        .flatMap(tuple -> databaseClient
            .sql("""
                INSERT INTO label_url
                  (created_at, last_modified_at, label_id, url, hash)
                VALUES (NOW(), NOW(), :id, :url, :hash)
                """)
            .bind("id", tuple.getT1())
            .bind("url", tuple.getT2())
            .bind("hash", tuple.getT2().hashCode())
            .then())
        .blockLast();
    for (long id = 1L; id < 11L; id++) {
      var dto = repository.findById(id).block();
      assertThat(dto).isNotNull();
      assertThat(dto.urls()).isNotNull().hasSize(1).allSatisfy(url -> assertThat(url).isNotBlank());
      long lid = id;
      if (id != 1L) {
        assertThat(dto.parentLabel()).isNotNull()
            .satisfies(parent -> assertThat(parent.id()).isEqualTo(lid - 1))
            .satisfies(parent -> assertThat(parent.name()).isNotBlank())
            .satisfies(
                parent -> assertThat(parent.getResourceURL()).contains(String.valueOf(lid - 1)));
      }
      if (id != 10L) {
        assertThat(dto.sublabels()).hasSize(1)
            .allSatisfy(ref -> assertThat(ref.id()).isEqualTo(lid + 1))
            .allSatisfy(ref -> assertThat(ref.name()).isNotBlank())
            .allSatisfy(ref -> assertThat(ref.getResourceURL()).contains(String.valueOf(lid + 1)));
      }
    }
  }

  @NotNull
  private Mono<Integer> fillParentColumn(Integer id) {
    if (id == 1) {
      return Mono.just(id);
    }
    return databaseClient.sql("""
            INSERT INTO label_sub_label
              (created_at, last_modified_at, parent_label_id, sub_label_id)
            VALUES (NOW(), NOW(), :parentId, :subLabelId)
            """)
        .bind("parentId", id - 1)
        .bind("subLabelId", id)
        .then()
        .thenReturn(id);
  }
}
