package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.projection.LabelDetailProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.FluentQuery.ReactiveFluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;
import java.util.stream.Collectors;

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
        return delegate.getLabelDetailById(id)
                .flatMap(mapProjectionToDto())
                .flatMap(withSublabels())
                .flatMap(withUrls());
    }

    @Override
    public Flux<LabelReleaseDTO> findReleasesByLabelId(Long id, Pageable pageable) {
        return delegate.findReleasesByLabelId(
        id,
        pageable.getOffset(),
        pageable.getPageSize(),
        getSortString(pageable));
    }

    @Override
    public Mono<Long> countReleasesByLabelId(Long id) {
        return delegate.countReleasesByLabelId(id);
    }

    private static String getSortString(Pageable pageable) {
        var sort = pageable.getSort().stream()
                .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
                .collect(Collectors.joining(", "));
        System.out.printf("sort resolved as %s\n", sort);
        return sort;
    }

    private static Function<LabelDetailProjection, Mono<LabelDetailDTO>> mapProjectionToDto() {
        return projection -> Mono.fromCallable(() -> LabelDetailDTO.builder()
                        .id(projection.id())
                        .profile(projection.profile())
                        .name(projection.name())
                        .contactInfo(projection.contactInfo())
                        .dataQuality(projection.dataQuality())
                        .parentLabel(LabelReferenceDTO.builder()
                                .id(projection.parentLabelId())
                                .name(projection.parentLabelName())
                                .build())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withSublabels() {
        return dto -> delegate.findSubLabels(dto.getId()).collectList().map(dto::withSublabels);
    }

    private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withUrls() {
        return dto -> delegate.findUrls(dto.getId()).collectList().map(dto::withUrls);
    }

    private static Function<ReactiveFluentQuery<Label>, Mono<Page<Label>>> getPageableQueryFunction(
            Pageable pageable) {
        return p -> p.as(Label.class).page(pageable);
    }
}
