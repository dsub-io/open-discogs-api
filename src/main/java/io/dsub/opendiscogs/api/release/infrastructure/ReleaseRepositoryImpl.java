package io.dsub.opendiscogs.api.release.infrastructure;

import static io.dsub.opendiscogs.jooq.Tables.ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.LABEL;
import static io.dsub.opendiscogs.jooq.Tables.LABEL_RELEASE_ITEM;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_CREDITED_ARTIST;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_FORMAT;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_GENRE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_STYLE;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_VIDEO;
import static io.dsub.opendiscogs.jooq.Tables.RELEASE_ITEM_WORK;
import static org.jooq.impl.DSL.asterisk;
import static org.jooq.impl.DSL.extract;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.lower;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.select;

import io.dsub.opendiscogs.api.release.domain.Release;
import io.dsub.opendiscogs.api.release.domain.ReleaseRepository;
import io.dsub.opendiscogs.api.release.dto.ReleaseArtistDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseDetailDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseFormatDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseLabelDTO;
import io.dsub.opendiscogs.api.release.dto.ReleaseVideoDTO;
import io.dsub.opendiscogs.api.release.query.ReleaseQuery;
import io.dsub.opendiscogs.jooq.tables.records.ReleaseItemRecord;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.SortField;
import org.jooq.TableField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepository {

  private final ReleaseR2dbcRepository delegate;
  private final DSLContext ctx;

  private static List<String> parseStrings(String in) {
    return in == null ? Collections.emptyList() : List.of(in.split(","));
  }

  @Override
  public Mono<Page<ReleaseDTO>> findAllBy(ReleaseQuery query, Pageable pageable) {
    Condition condition = searchCondition(query);

    Mono<List<ReleaseDTO>> items = Flux.from(ctx.selectFrom(RELEASE_ITEM)
            .where(condition)
            .orderBy(sortFields(pageable))
            .offset(Math.toIntExact(pageable.getOffset()))
            .limit(pageable.getPageSize()))
        .map(ReleaseRepositoryImpl::toRelease)
        .map(ReleaseDTO::fromRelease)
        .collectList();

    Mono<Long> total = Mono.from(ctx.selectCount()
            .from(RELEASE_ITEM)
            .where(condition))
        .map(record -> record.value1().longValue());

    return Mono.zip(items, total)
        .map(result -> new PageImpl<>(result.getT1(), pageable, result.getT2()));
  }

  @Override
  public Mono<ReleaseDetailDTO> getById(Long id) {
    return Mono.zip(delegate.findById(id), getGenres(id), getStyles(id), getArtists(id),
            getLabels(id), getCompanies(id), getFormats(id), getVideos(id))
        .map(tuple -> ReleaseDetailDTO.fromRelease(tuple.getT1())
            .withGenres(tuple.getT2())
            .withStyles(tuple.getT3())
            .withArtists(tuple.getT4())
            .withLabels(tuple.getT5())
            .withCompanies(tuple.getT6())
            .withFormats(tuple.getT7())
            .withVideos(tuple.getT8()));
  }

  private static Condition searchCondition(ReleaseQuery query) {
    Condition condition = noCondition();
    if (query.title() != null) {
      condition = condition.and(RELEASE_ITEM.TITLE.containsIgnoreCase(query.title()));
    }
    if (query.country() != null) {
      condition = condition.and(
          lower(RELEASE_ITEM.COUNTRY).eq(query.country().toLowerCase(Locale.ROOT)));
    }
    if (query.year() != null) {
      condition = condition
          .and(RELEASE_ITEM.HAS_VALID_YEAR.isTrue())
          .and(extract(RELEASE_ITEM.RELEASE_DATE, DatePart.YEAR).eq(query.year()));
    }
    if (query.month() != null) {
      condition = condition
          .and(RELEASE_ITEM.HAS_VALID_MONTH.isTrue())
          .and(extract(RELEASE_ITEM.RELEASE_DATE, DatePart.MONTH).eq(query.month()));
    }
    if (query.isMaster() != null) {
      condition = condition.and(RELEASE_ITEM.IS_MASTER.eq(query.isMaster()));
    }
    return condition;
  }

  private static List<SortField<?>> sortFields(Pageable pageable) {
    List<SortField<?>> fields = pageable.getSort().stream()
        .map(ReleaseRepositoryImpl::sortField)
        .filter(Objects::nonNull)
        .toList();
    return fields.isEmpty() ? List.of(RELEASE_ITEM.ID.asc()) : fields;
  }

  private static SortField<?> sortField(Sort.Order order) {
    TableField<ReleaseItemRecord, ?> field = switch (order.getProperty()) {
      case "id" -> RELEASE_ITEM.ID;
      case "title" -> RELEASE_ITEM.TITLE;
      case "country" -> RELEASE_ITEM.COUNTRY;
      case "released_year", "released_month" -> RELEASE_ITEM.RELEASE_DATE;
      default -> null;
    };
    if (field == null) {
      return null;
    }
    return order.isAscending() ? field.asc() : field.desc();
  }

  private static Release toRelease(ReleaseItemRecord record) {
    return Release.builder()
        .id(record.getId().longValue())
        .title(record.getTitle())
        .country(record.getCountry())
        .dataQuality(record.getDataQuality())
        .releaseDate(record.getReleaseDate())
        .hasValidYear(record.getHasValidYear())
        .hasValidMonth(record.getHasValidMonth())
        .hasValidDay(record.getHasValidDay())
        .listedReleaseDate(record.getListedReleaseDate())
        .isMaster(record.getIsMaster())
        .masterId(record.getMasterId() == null ? null : record.getMasterId().longValue())
        .notes(record.getNotes())
        .status(record.getStatus())
        .build();
  }

  private Mono<List<ReleaseFormatDTO>> getFormats(Long releaseId) {
    return Flux.from(
            ctx.select(RELEASE_ITEM_FORMAT.NAME, RELEASE_ITEM_FORMAT.QUANTITY,
                    RELEASE_ITEM_FORMAT.DESCRIPTION)
                .from(RELEASE_ITEM_FORMAT)
                .where(RELEASE_ITEM_FORMAT.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> ReleaseFormatDTO.builder()
            .name(record.get(RELEASE_ITEM_FORMAT.NAME))
            .quantity(record.get(RELEASE_ITEM_FORMAT.QUANTITY))
            .descriptions(parseStrings(record.get(RELEASE_ITEM_FORMAT.DESCRIPTION)))
            .build())
        .collectList();
  }

  private Mono<List<String>> getGenres(Long releaseId) {
    return Flux.from(ctx.select(RELEASE_ITEM_GENRE.GENRE)
            .from(RELEASE_ITEM_GENRE)
            .where(RELEASE_ITEM_GENRE.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> record.get(RELEASE_ITEM_GENRE.GENRE))
        .collectList();
  }

  private Mono<List<String>> getStyles(Long releaseId) {
    return Flux.from(ctx.select(RELEASE_ITEM_STYLE.STYLE)
            .from(RELEASE_ITEM_STYLE)
            .where(RELEASE_ITEM_STYLE.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> record.get(RELEASE_ITEM_STYLE.STYLE))
        .collectList();
  }

  private Mono<List<ReleaseArtistDTO>> getArtists(Long releaseId) {
    var releaseArtists = select(ARTIST.ID, ARTIST.NAME, field("'Main'", String.class).as("role"))
        .from(RELEASE_ITEM_ARTIST)
        .join(ARTIST).on(ARTIST.ID.eq(RELEASE_ITEM_ARTIST.ARTIST_ID))
        .where(RELEASE_ITEM_ARTIST.RELEASE_ITEM_ID.eq(releaseId.intValue()));

    var creditedArtists = select(ARTIST.ID, ARTIST.NAME, RELEASE_ITEM_CREDITED_ARTIST.ROLE)
        .from(RELEASE_ITEM_CREDITED_ARTIST)
        .join(ARTIST).on(ARTIST.ID.eq(RELEASE_ITEM_CREDITED_ARTIST.ARTIST_ID))
        .where(RELEASE_ITEM_CREDITED_ARTIST.RELEASE_ITEM_ID.eq(releaseId.intValue()));

    var relatedArtists = select(asterisk()).from(releaseArtists).unionAll(creditedArtists)
        .asTable("artist");

    return Flux.from(ctx.select(field("id"), field("name"),
                field("string_agg(distinct trim(artist.role), ',')", String.class).as("role"))
            .from(relatedArtists)
            .groupBy(field("id"), field("name"))
            .orderBy(field("id")))
        .map(record -> ReleaseArtistDTO.builder()
            .id(record.get("id", Long.class))
            .name(record.get("name", String.class))
            .role(record.get("role", String.class))
            .build())
        .collectList();
  }

  private Mono<List<ReleaseLabelDTO>> getCompanies(Long releaseId) {
    return Flux.from(ctx.select(LABEL.ID, LABEL.NAME, RELEASE_ITEM_WORK.WORK)
            .from(RELEASE_ITEM_WORK)
            .join(LABEL).on(RELEASE_ITEM_WORK.LABEL_ID.eq(LABEL.ID))
            .where(RELEASE_ITEM_WORK.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> ReleaseLabelDTO.builder()
            .id(record.get(LABEL.ID).longValue())
            .name(record.get(LABEL.NAME))
            .categoryNotation(record.get(RELEASE_ITEM_WORK.WORK))
            .build())
        .collectList();
  }

  private Mono<List<ReleaseLabelDTO>> getLabels(Long releaseId) {
    return Flux.from(ctx.select(LABEL.ID, LABEL.NAME, LABEL_RELEASE_ITEM.CATEGORY_NOTATION)
            .from(LABEL_RELEASE_ITEM)
            .join(LABEL).on(LABEL.ID.eq(LABEL_RELEASE_ITEM.LABEL_ID))
            .where(LABEL_RELEASE_ITEM.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> ReleaseLabelDTO.builder()
            .id(record.get(LABEL.ID).longValue())
            .name(record.get(LABEL.NAME))
            .categoryNotation(record.get(LABEL_RELEASE_ITEM.CATEGORY_NOTATION))
            .build())
        .collectList();
  }

  private Mono<List<ReleaseVideoDTO>> getVideos(Long releaseId) {
    return Flux.from(
            ctx.select(RELEASE_ITEM_VIDEO.TITLE, RELEASE_ITEM_VIDEO.DESCRIPTION,
                    RELEASE_ITEM_VIDEO.URL)
                .from(RELEASE_ITEM_VIDEO)
                .where(RELEASE_ITEM_VIDEO.RELEASE_ITEM_ID.eq(releaseId.intValue())))
        .map(record -> record.into(ReleaseVideoDTO.class))
        .collectList();
  }
}
