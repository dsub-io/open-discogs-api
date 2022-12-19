package io.dsub.sweatboys.opendiscogs.api.master.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.domain.MasterRepository;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class MasterRepositoryImpl implements MasterRepository {

  private final MasterR2dbcRepository repository;

  private static String getSortString(Pageable pageable) {
    return pageable.getSort().stream()
        .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
        .collect(Collectors.joining(", "));
  }

  @Override
  public Mono<Page<Master>> findAllBy(Example<Master> example, Pageable pageable) {
    return repository.findBy(example, q -> q.page(pageable));
  }

  @Override
  public Mono<MasterDetailDTO> findById(Long id) {
    return repository.findById(id)
        .flatMap(MasterDetailDTO::fromMaster)
        .flatMap(withMainRelease())
        .flatMap(withStyles())
        .flatMap(withGenres())
        .flatMap(withArtists())
        .flatMap(withVideos());
  }

  @Override
  public Flux<MasterReleaseDTO> findReleasesByMasterId(Long id, Pageable pageable) {
    return repository.findReleasesByMasterId(id, getSortString(pageable), pageable.getOffset(),
        pageable.getPageSize());
  }

  @Override
  public Mono<Long> countReleasesByMasterId(Long masterId) {
    return repository.countReleasesByMasterId(masterId);
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withMainRelease() {
    return dto -> repository.findMainReleaseId(dto.id())
        .flatMap(releaseId -> Mono.fromCallable(() -> dto.withMainRelease(releaseId))
            .subscribeOn(Schedulers.boundedElastic()))
        .defaultIfEmpty(dto);
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withGenres() {
    return dto -> repository.findMasterGenres(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(genres -> Mono.fromCallable(() -> dto.withGenres(genres))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withArtists() {
    return dto -> repository.findMasterArtists(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(artists -> Mono.fromCallable(() -> dto.withArtists(artists))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withStyles() {
    return dto -> repository.findMasterStyles(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(styles -> Mono.fromCallable(() -> dto.withStyles(styles))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withVideos() {
    return dto -> repository.findMasterVideos(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(videos -> Mono.fromCallable(() -> dto.withVideos(videos))
            .subscribeOn(Schedulers.boundedElastic()));
  }
}
