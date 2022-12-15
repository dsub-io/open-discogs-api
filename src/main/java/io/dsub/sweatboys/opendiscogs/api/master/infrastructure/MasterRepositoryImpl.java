package io.dsub.sweatboys.opendiscogs.api.master.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.domain.MasterRepository;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class MasterRepositoryImpl implements MasterRepository {

  private final MasterR2dbcRepository repository;

  @Override
  public Mono<Page<Master>> findAllBy(Example<Master> example, Pageable pageable) {
    return repository.findBy(example, q -> q.page(pageable));
  }

  @Override
  public Mono<MasterDetailDTO> findById(Long id) {
    return repository.findById(id)
        .flatMap(MasterDetailDTO::fromMaster)
        .flatMap(withStyles())
        .flatMap(withGenres())
        .flatMap(withArtists());
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withGenres() {
    return dto -> repository.findMasterGenres(dto.id())
        .collectList()
        .flatMap(genres -> Mono.fromCallable(() -> dto.withGenres(genres)).subscribeOn(
            Schedulers.boundedElastic()));
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withArtists() {
    return dto -> repository.findMasterArtists(dto.id())
        .collectList()
        .flatMap(artists -> Mono.fromCallable(() -> dto.withArtists(artists)).subscribeOn(
            Schedulers.boundedElastic()));
  }

  private Function<MasterDetailDTO, Mono<? extends MasterDetailDTO>> withStyles() {
    return dto -> repository.findMasterStyles(dto.id())
        .collectList()
        .flatMap(styles -> Mono.fromCallable(() -> dto.withStyles(styles)).subscribeOn(
            Schedulers.boundedElastic()));
  }
}
