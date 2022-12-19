package io.dsub.sweatboys.opendiscogs.api.release.infrastructure;

import static io.dsub.opendiscogs.jooq.Tables.ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.GENRE;
import static io.dsub.opendiscogs.jooq.Tables.LABEL;
import static io.dsub.opendiscogs.jooq.Tables.LABEL_RELEASE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_CONTRACT;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_CREDITED_ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_FORMAT;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_GENRE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_STYLE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_VIDEO;
import static io.dsub.opendiscogs.jooq.Tables.STYLE;
import static org.jooq.impl.DSL.asterisk;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.select;

import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.release.domain.ReleaseRepository;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseArtistDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseFormatDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseLabelDTO;
import io.dsub.sweatboys.opendiscogs.api.release.dto.ReleaseVideoDTO;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepository {

  private final ReleaseR2dbcRepository delegate;
  private final DSLContext ctx;

  private static List<String> parseStrings(String in) {
    return in == null ? Collections.emptyList() : List.of(in.split(","));
  }

  private static Function<Page<Release>, Mono<? extends Page<ReleaseDTO>>> toReleaseDTO(
      Pageable pageable) {
    return page -> Mono.fromCallable(
        () -> new PageImpl<>(page.get().map(ReleaseDTO::fromRelease).toList(), pageable,
            page.getTotalElements()));
  }

  @Override
  public Mono<Page<ReleaseDTO>> findAllBy(Example<Release> example, Pageable pageable) {
    return delegate.findBy(example, q -> q.page(pageable))
        .flatMap(toReleaseDTO(pageable))
        .publishOn(Schedulers.boundedElastic());
  }

  @Override
  public Mono<ReleaseDetailDTO> getById(Long id) {
    return Mono.zip(delegate.findById(id), getGenres(id), getStyles(id), getArtists(id),
            getLabels(id), getContracts(id), getFormats(id), getVideos(id))
        .map(tuple -> ReleaseDetailDTO.fromRelease(tuple.getT1())
            .withGenres(tuple.getT2())
            .withStyles(tuple.getT3())
            .withArtists(tuple.getT4())
            .withLabels(tuple.getT5())
            .withCompanies(tuple.getT6())
            .withFormats(tuple.getT7())
            .withVideos(tuple.getT8()))
        .publishOn(Schedulers.boundedElastic());
  }

  private Mono<List<ReleaseFormatDTO>> getFormats(Long releaseId) {
    return Flux.from(
            ctx.select(RELEASE_FORMAT.NAME, RELEASE_FORMAT.QUANTITY, RELEASE_FORMAT.DESCRIPTION)
                .from(RELEASE_FORMAT)
                .where(RELEASE_FORMAT.RELEASE_ID.eq(releaseId.intValue())))
        .map(record -> ReleaseFormatDTO.builder()
            .name(record.getValue("name", String.class))
            .quantity(record.getValue("quantity", Integer.class))
            .descriptions(parseStrings(record.getValue("description", String.class)))
            .build())
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<String>> getGenres(Long releaseId) {
    return Flux.from(ctx.select(GENRE.NAME)
            .from(RELEASE_GENRE)
            .join(GENRE).on(GENRE.ID.eq(RELEASE_GENRE.GENRE_ID))
            .where(RELEASE_GENRE.RELEASE_ID.eq(releaseId.intValue())))
        .map(r -> r.getValue(GENRE.NAME, String.class))
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<String>> getStyles(Long releaseId) {
    return Flux.from(ctx.select(STYLE.NAME)
            .from(RELEASE_STYLE)
            .join(STYLE).on(STYLE.ID.eq(RELEASE_STYLE.STYLE_ID))
            .where(RELEASE_STYLE.RELEASE_ID.eq(releaseId.intValue())))
        .map(r -> r.getValue(STYLE.NAME, String.class))
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<ReleaseArtistDTO>> getArtists(Long releaseId) {
    var releaseArtists = select(ARTIST.ID, ARTIST.NAME, field("'Main'", String.class).as("role"))
        .from(RELEASE_ARTIST)
        .join(ARTIST).on(ARTIST.ID.eq(RELEASE_ARTIST.ARTIST_ID))
        .where(RELEASE_ARTIST.RELEASE_ID.eq(releaseId.intValue()));

    var creditedArtists = select(ARTIST.ID, ARTIST.NAME, RELEASE_CREDITED_ARTIST.ROLE)
        .from(RELEASE_CREDITED_ARTIST)
        .join(ARTIST).on(ARTIST.ID.eq(RELEASE_CREDITED_ARTIST.ARTIST_ID))
        .where(RELEASE_CREDITED_ARTIST.RELEASE_ID.eq(releaseId.intValue()));

    var relatedArtists = select(asterisk()).from(releaseArtists).unionAll(creditedArtists)
        .asTable("artist");

    return Flux.from(ctx.select(field("id"), field("name"),
                field("string_agg(distinct trim(artist.role), ',')", String.class).as("role"))
            .from(relatedArtists)
            .groupBy(field("id"), field("name"))
            .orderBy(field("id")))
        .map(record -> ReleaseArtistDTO.builder()
            .id(record.getValue("id", Long.class))
            .name(record.getValue("name", String.class))
            .role(record.getValue("role", String.class))
            .build())
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<ReleaseLabelDTO>> getContracts(Long releaseId) {
    return Flux.from(ctx.select(LABEL.ID, LABEL.NAME, RELEASE_CONTRACT.CONTRACT)
            .from(RELEASE_CONTRACT)
            .join(LABEL).on(RELEASE_CONTRACT.LABEL_ID.eq(LABEL.ID))
            .where(RELEASE_CONTRACT.RELEASE_ID.eq(releaseId.intValue())))
        .flatMap(record -> Mono.fromCallable(() -> ReleaseLabelDTO.builder()
            .id(record.get("id", Long.class))
            .name(record.get("name", String.class))
            .categoryNotation(record.get("contract", String.class))
            .build()))
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<ReleaseLabelDTO>> getLabels(Long releaseId) {
    return Flux.from(ctx.select(LABEL.ID, LABEL.NAME)
            .from(LABEL_RELEASE)
            .join(LABEL).on(LABEL.ID.eq(LABEL_RELEASE.LABEL_ID))
            .where(LABEL_RELEASE.RELEASE_ID.eq(releaseId.intValue())))
        .flatMap(record -> Mono.fromCallable(() -> ReleaseLabelDTO.builder()
            .id(record.get("id", Long.class))
            .name(record.get("name", String.class))
            .categoryNotation("Label")
            .build()))
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }

  private Mono<List<ReleaseVideoDTO>> getVideos(Long releaseId) {
    return Flux.from(ctx.select(RELEASE_VIDEO.TITLE, RELEASE_VIDEO.DESCRIPTION, RELEASE_VIDEO.URL)
            .from(RELEASE_VIDEO)
            .where(RELEASE_VIDEO.RELEASE_ID.eq(releaseId.intValue())))
        .map(record -> record.into(ReleaseVideoDTO.class))
        .collectList()
        .defaultIfEmpty(Collections.emptyList());
  }
}
