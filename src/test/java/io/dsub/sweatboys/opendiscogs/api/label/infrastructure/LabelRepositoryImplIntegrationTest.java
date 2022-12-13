package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.stream.IntStream;

public class LabelRepositoryImplIntegrationTest extends AbstractDatabaseIntegrationTest {
    @Autowired
    LabelR2dbcRepository r2dbcRepository;
    @Autowired
    DatabaseClient databaseClient;
    LabelRepository repository;
    List<Label> labels;

//    @BeforeEach
//    void setUp {
//        repository = new LabelRepositoryImpl(r2dbcRepository);
//        var items = IntStream.rangeClosed(1,10)
//                .mapToObj(i -> Label.)
//    }
}
