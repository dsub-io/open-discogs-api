package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.label.application.LabelService;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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
        var items = IntStream.rangeClosed(1,10)
                .mapToObj(i -> Label.builder()
                        .id((long) i)
                        .contactInfo(TestUtil.getRandomString())
                        .dataQuality(TestUtil.getRandomString())
                        .name(TestUtil.getRandomString())
                        .profile(TestUtil.getRandomString())
                        .build())
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
    void findAllByReturnsByName() {
        for (Label label : labels) {
            var example = Example.of(
                    Label.builder()
                            .name(label.getName())
                            .build(),
                    ExampleMatcher
                            .matching()
                            .withIgnoreNullValues()
                            .withIgnoreCase()
            );
        var pageable = PageRequest.of(0, 5);
        var result = repository.findAllBy(example,pageable).block();
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getNumberOfElements()).isEqualTo(1);
        assertThat(result.getContent()).isNotNull().isNotEmpty().hasSize(1);
        }
    }

//    @Test
//    void getLabelReturnsSublabel() {
//        var label = repository.findById((1L)).block();
//        assertThat(label).isNotNull();
////        for(LabelReferenceDTO referenceDTO : label.getParentLabel()) {
////            assertThat(referenceDTO).is
////        }
//        for(List<LabelReferenceDTO> references : List.of(label.getSublabels())) {
//            assertThat(references)
//                    .isNotNull()
//                    .isNotEmpty()
//                    .allSatisfy(ref -> assertThat(ref.id()).isNotNull().isNotEqualTo(1L))
//                    .allSatisfy(ref -> assertThat(ref.name()).isNotNull().isNotEqualTo(label.getName()))
//                    .hasSize(1);
//            }
//        for (String url : label.getUrls()) {
//            assertThat(url).isNotNull().isNotBlank();
//    }
//    }
}
