package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReleaseDTO;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.dsub.opendiscogs.jooq.Tables.*;
import static io.dsub.sweatboys.opendiscogs.api.core.util.JooqUtility.getSortFields;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {
    private static final List<Field<?>> ARTIST_RELEASES_SELECT_FIELDS;
    private static final List<Field<?>> ARTIST_RELEASES_MAIN_SELECT_FIELDS;
    private static final List<Field<?>> ARTIST_RELEASES_ROLES_SELECT_FIELDS;
    private static final List<Field<?>> RELEASES_FIELDS;

    /* initialize fields */
    static {
        RELEASES_FIELDS = new ArrayList<>(Arrays.asList(RELEASE.as("r").fields()));
        ARTIST_RELEASES_SELECT_FIELDS = new ArrayList<>(RELEASES_FIELDS);
        ARTIST_RELEASES_SELECT_FIELDS.add(DSL.field("string_agg(distinct trim(r.role), ',')", String.class).as("role"));
        ARTIST_RELEASES_MAIN_SELECT_FIELDS = new ArrayList<>(RELEASES_FIELDS);
        ARTIST_RELEASES_MAIN_SELECT_FIELDS.add(DSL.field("'Main'").as("role"));
        ARTIST_RELEASES_ROLES_SELECT_FIELDS = new ArrayList<>(RELEASES_FIELDS);
        ARTIST_RELEASES_ROLES_SELECT_FIELDS.add(DSL.field(RELEASE_CREDITED_ARTIST.ROLE.as("role")));
    }

    private final ArtistR2dbcRepository delegate;
    private final DSLContext ctx;

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
        return Flux.from(ctx
                .select(ARTIST_RELEASES_SELECT_FIELDS)
                .from(ARTIST_RELEASES_AS_MAIN(id)
                        .unionAll(ARTISTS_RELEASES_WHERE_PARTICIPATED(id))
                        .asTable("r"))
                .groupBy(RELEASES_FIELDS)
                .orderBy(getSortFields(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()))
                .map(toArtistReleaseDTO())
                .publishOn(Schedulers.boundedElastic());
    }

    private static Function<Record, ArtistReleaseDTO> toArtistReleaseDTO() {
        return record -> record.into(ArtistReleaseDTO.class);
    }

    private static SelectConditionStep<Record> ARTISTS_RELEASES_WHERE_PARTICIPATED(Long id) {
        return select(ARTIST_RELEASES_ROLES_SELECT_FIELDS)
                .from(RELEASE_CREDITED_ARTIST)
                .join(RELEASE.as("r")).on(RELEASE.as("r").ID.eq(RELEASE_CREDITED_ARTIST.RELEASE_ID))
                .where(RELEASE_CREDITED_ARTIST.ARTIST_ID.eq(id.intValue()));
    }

    private static SelectConditionStep<Record> ARTIST_RELEASES_AS_MAIN(Long id) {
        return select(ARTIST_RELEASES_MAIN_SELECT_FIELDS)
                .from(RELEASE_ARTIST)
                .join(RELEASE.as("r")).on(RELEASE_ARTIST.RELEASE_ID.eq(RELEASE.as("r").ID))
                .where(RELEASE_ARTIST.ARTIST_ID.eq(id.intValue()));
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
