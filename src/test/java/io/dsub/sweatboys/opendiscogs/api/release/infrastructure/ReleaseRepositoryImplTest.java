package io.dsub.sweatboys.opendiscogs.api.release.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static reactor.test.StepVerifier.create;

class ReleaseRepositoryImplTest {
    @Mock
    ReleaseR2dbcRepository delegate;
    @InjectMocks
    ReleaseRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBy() {
        var releases = IntStream.rangeClosed(1, 10)
                .mapToObj(id -> TestUtil.getInstanceOf(Release.class).withId((long) id))
                .toList();
        var releaseDTOs = releases.stream().map(ReleaseDTO::fromRelease).toList();

        var page = new PageImpl<>(releases, PageRequest.of(1, 10), 10);
        var expected = new PageImpl<>(releaseDTOs, PageRequest.of(1, 10), 10);

        BDDMockito.given(delegate.findBy(any(), any())).willReturn(Mono.just(page));

        create(repository.findAllBy(Example.of(Release.builder().build()), PageRequest.of(1, 10)))
                .expectNext(expected)
                .verifyComplete();
    }
}