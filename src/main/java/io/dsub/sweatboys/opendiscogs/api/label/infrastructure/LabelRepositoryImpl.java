package io.dsub.sweatboys.opendiscogs.api.label.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.domain.LabelRepository;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.label.dto.LabelReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.label.projection.LabelDetailProjection;
import jakarta.annotation.Nullable;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.FluentQuery.ReactiveFluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {

  private final LabelR2dbcRepository delegate;

  private static String getSortString(Pageable pageable) {
    return pageable.getSort().stream()
        .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
        .collect(Collectors.joining(", "));
  }

  private static Function<LabelDetailProjection, Mono<LabelDetailDTO>> mapProjectionToDto() {
    return projection -> Mono.fromCallable(() -> LabelDetailDTO.builder()
            .id(projection.id())
            .profile(normalize(projection.profile()))
            .name(normalize(projection.name()))
            .contactInfo(normalize(projection.contactInfo()))
            .dataQuality(normalize(projection.dataQuality()))
            .parentLabel(getParentLabelMapped(projection))
            .build())
        .subscribeOn(Schedulers.boundedElastic());
  }

  private static LabelReferenceDTO getParentLabelMapped(LabelDetailProjection projection) {
    if (hasParent(projection)) {
      return LabelReferenceDTO.builder()
          .id(projection.parentLabelId())
          .name(normalize(projection.parentLabelName()))
          .build();
    }
    return null;
  }

  private static boolean hasParent(LabelDetailProjection projection) {
    return projection.parentLabelId() != null;
  }

  private static String normalize(@Nullable String in) {
    return StringUtility.normalize(in);
  }

  private static Function<ReactiveFluentQuery<Label>, Mono<Page<Label>>> getPageableQueryFunction(
      Pageable pageable) {
    return p -> p.as(Label.class).page(pageable);
  }

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

  private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withSublabels() {
//        return dto -> delegate.findSubLabels(dto.id()).collectList().map(dto::withSublabels);
    return dto -> delegate.findSubLabels(dto.id())
        .collectList()
        .flatMap(sublabels -> Mono.fromCallable(() -> dto.withSublabels(sublabels))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<LabelDetailDTO, Mono<? extends LabelDetailDTO>> withUrls() {
    return dto -> delegate.findUrls(dto.id())
        .collectList()
        .flatMap(urls -> Mono.fromCallable(() -> dto.withUrls(urls))
            .subscribeOn(Schedulers.boundedElastic()));
  }
}
