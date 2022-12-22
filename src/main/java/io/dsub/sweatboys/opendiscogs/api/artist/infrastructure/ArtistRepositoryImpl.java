package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import static io.dsub.opendiscogs.jooq.Tables.RELEASE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_CREDITED_ARTIST;
import static io.dsub.sweatboys.opendiscogs.api.core.util.JooqUtility.getSortFields;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.select;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {

  private final ArtistR2dbcRepository delegate;
  private final DSLContext ctx;

  private static Function<Record, ArtistReleaseDTO> toArtistReleaseDTO() {
    return record -> record.into(ArtistReleaseDTO.class);
  }

  private static String getSortString(Pageable pageable) {
    return pageable.getSort().stream()
        .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
        .collect(Collectors.joining(", "));
  }

  private static Function<Artist, Mono<? extends ArtistDetailDTO>> toDTO() {
    return ArtistDetailDTO::fromArtist;
  }

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
    return Flux.from(
            ctx.select(RELEASE.ID.as("id"),
                    RELEASE.TITLE.as("title"),
                    RELEASE.COUNTRY.as("country"),
                    RELEASE.DATA_QUALITY.as("data_quality"),
                    RELEASE.RELEASED_YEAR.as("released_year"),
                    RELEASE.RELEASED_MONTH.as("released_month"),
                    RELEASE.RELEASED_DAY.as("released_day"),
                    RELEASE.LISTED_RELEASE_DATE.as("listed_release_date"),
                    RELEASE.IS_MASTER.as("is_master"),
                    RELEASE.NOTES.as("notes"),
                    RELEASE.STATUS.as("status"),
                    RELEASE.MASTER_ID.as("master_id"),
                    field("role", String.class))
                .from(select(field("release_id", Long.class),
                    field("string_agg(distinct trim(r.role), ',')", String.class).as("role"))
                    .from(
                        select(field("release_id", Long.class),
                            field("'Main'", String.class).as("role"))
                            .from(RELEASE_ARTIST).where(RELEASE_ARTIST.ARTIST_ID.eq(id.intValue()))
                            .unionAll(
                                select(field("release_id", Long.class), field("role", String.class))
                                    .from(RELEASE_CREDITED_ARTIST)
                                    .where(RELEASE_CREDITED_ARTIST.ARTIST_ID.eq(id.intValue())))
                            .asTable("r")
                    )
                    .groupBy(field("release_id")))
                .join(RELEASE).on(RELEASE.ID.eq(field("release_id", Integer.class)))
                .orderBy(getSortFields(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()))
        .map(toArtistReleaseDTO())
        .publishOn(Schedulers.boundedElastic());
  }

  @Override
  public Mono<Long> countReleasesByArtistId(Long id) {
    return delegate.countReleasesByArtistId(id);
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
