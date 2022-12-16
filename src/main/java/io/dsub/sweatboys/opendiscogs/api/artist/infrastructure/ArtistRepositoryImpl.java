package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {

  private final ArtistR2dbcRepository delegate;

  @Override
  public Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable) {
    return delegate.findBy(example, q -> q.page(pageable));
  }

  @Override
  public Mono<ArtistDetailDTO> findById(Long id) {
    return delegate.findById(id)
        .flatMap(toDTO())
        .flatMap(withArtistAliases())
        .flatMap(withArtistGroups())
        .flatMap(withArtistMembers())
        .flatMap(withUrls())
        .flatMap(withNameVariations());
  }

  @Override
  public Flux<ArtistReleaseDTO> findReleasesByArtistId(Long id, Pageable pageable) {
    return delegate.findReleasesByArtistId(
        id,
        pageable.getOffset(),
        pageable.getPageSize(),
        getSortString(pageable));
  }

  @Override
  public Mono<Long> countReleasesByArtistId(Long id) {
    return delegate.countReleasesByArtistId(id);
  }

  private static String getSortString(Pageable pageable) {
    return pageable.getSort().stream()
        .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
        .collect(Collectors.joining(", "));
  }

  private static Function<Artist, Mono<? extends ArtistDetailDTO>> toDTO() {
    return ArtistDetailDTO::fromArtist;
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistMembers() {
    return dto -> delegate.findMemberArtists(dto.id()).collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(members -> Mono.fromCallable(() -> dto.withMembers(members))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistGroups() {
    return dto -> delegate.findGroupArtists(dto.id()).collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(groups -> Mono.fromCallable(() -> dto.withGroups(groups))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistAliases() {
    return dto -> delegate.findAliasArtists(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(aliases -> Mono.fromCallable(() -> dto.withAliases(aliases))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withUrls() {
    return dto -> delegate.findUrls(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(urls -> Mono.fromCallable(() -> dto.withUrls(urls))
            .subscribeOn(Schedulers.boundedElastic()));
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withNameVariations() {
    return dto -> delegate.findNameVariations(dto.id())
        .collectList()
        .defaultIfEmpty(Collections.emptyList())
        .flatMap(nameVariations -> Mono.fromCallable(() -> dto.withNameVariations(nameVariations))
            .subscribeOn(Schedulers.boundedElastic()));
  }
}
