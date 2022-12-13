package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.FluentQuery.ReactiveFluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {
    private final LabelR2dbcRepository delegate;

    @Override
    public Mono<Page<Label>> findAllBy(Example<Label> example, Pageable pageable) {
        return delegate.findBy(example, getPageableQueryFunction(pageable));
    }

    @Override
    public Mono<LabelDetailDTO> findById(Long id) {
        return delegate.findById(id)
                .flatMap(toDTO())
                .flatMap(withSublabels())
                .flatMap(withUrls());
    }

    private static Function<Label, Mono<? extends LabelDetailDTO>> toDTO() {
        return LabelDetailDTO::fromLabel;
    }
    private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withSublabels() {
        return dto -> delegate.findSublabels(dto.getId()).collectList().map(dto::withSublabels);
    }

    private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withUrls() {
        return dto -> delegate.findUrls(dto.getId()).collectList().map(dto::withUrls);
    }

    private static Function<ReactiveFluentQuery<Label>, Mono<Page<Label>>> getPageableQueryFunction(
            Pageable pageable) {
        return p -> p.as(Label.class).page(pageable);
    }

}
