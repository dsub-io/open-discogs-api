package io.dsub.opendiscogs.api.release.infrastructure;


import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.opendiscogs.api.artist.domain.Artist;
import io.dsub.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.opendiscogs.api.label.domain.Label;
import io.dsub.opendiscogs.api.label.infrastructure.LabelR2dbcRepository;
import io.dsub.opendiscogs.api.master.domain.Master;
import io.dsub.opendiscogs.api.master.infrastructure.MasterR2dbcRepository;
import io.dsub.opendiscogs.api.release.domain.Release;
import io.dsub.opendiscogs.api.release.query.ReleaseQuery;
import io.dsub.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.opendiscogs.api.test.util.TestUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;

public class ReleaseRepositoryImplIntegrationTest extends AbstractDatabaseIntegrationTest {

  @Autowired
  ReleaseR2dbcRepository delegate;
  @Autowired
  ArtistR2dbcRepository artistR2dbcRepository;
  @Autowired
  LabelR2dbcRepository labelR2dbcRepository;
  @Autowired
  MasterR2dbcRepository masterR2dbcRepository;
  @Autowired
  DSLContext ctx;
  @Autowired
  DatabaseClient client;

  ReleaseRepositoryImpl repository;

  @BeforeEach
  void setUp() {
    repository = new ReleaseRepositoryImpl(delegate, ctx);
  }

  @AfterEach
  void tearDown() {
    initDatabase(client);
  }

  @Test
  void findByIdReturnsAllRelations() {
    // long... prep
    var master = prepareMaster();
    var release = getEntityOf(Release.class)
        .withId(1L)
        .withMasterId(master.getId())
        .withReleaseDate(LocalDate.of(2010, 3, 12))
        .withHasValidYear(true)
        .withHasValidMonth(true)
        .withHasValidDay(true);
    release.setAsNew();
    delegate.save(release).block();

    var artists = prepareArtists();
    var labels = prepareLabels();
    var genres = prepareGenres();
    var styles = prepareStyles();
    var urls = prepareVideos();
    prepareFormats();
    // do
    var dto = repository.getById(1L).block();
    assertThat(dto).isNotNull();
    assertThat(dto.getArtists()).hasSize(3);
    for (int i = 0; i < dto.getArtists().size(); i++) {
      var item = dto.getArtists().get(i);
      assertThat(item.getName()).contains(artists.get(i).getName());
      assertThat(item.getId()).isEqualTo(artists.get(i).getId());
      String expectedRole = i == 2 ? "Main,test_role" : "Main";
      assertThat(item.getRole()).isEqualTo(expectedRole);
    }

    assertThat(dto.getLabels()).hasSize(1);
    var label = dto.getLabels().get(0);
    assertThat(label).isNotNull();
    assertThat(label.getCategoryNotation()).isEqualTo("test_category_notation");
    assertThat(label.getName()).isEqualTo(labels.get(0).getName());
    assertThat(label.getId()).isEqualTo(1);

    assertThat(dto.getCompanies()).hasSize(1);
    var company = dto.getCompanies().get(0);
    assertThat(company).isNotNull();
    assertThat(company.getCategoryNotation()).isEqualTo("test_contract");
    assertThat(company.getName()).isEqualTo(labels.get(1).getName());
    assertThat(company.getId()).isEqualTo(2);

    assertThat(dto.getGenres().stream().distinct().collect(Collectors.toList()))
        .hasSize(3)
        .allSatisfy(genre -> assertThat(genres).contains(genre));
    assertThat(dto.getStyles().stream().distinct().collect(Collectors.toList()))
        .hasSize(3)
        .allSatisfy(style -> assertThat(styles).contains(style));

    assertThat(dto.getVideos()).hasSize(5)
        .allSatisfy(vid -> assertThat(vid.getTitle()).isEqualTo("test_title"))
        .allSatisfy(vid -> assertThat(vid.getDescription()).isEqualTo("test_description"))
        .allSatisfy(vid -> assertThat(vid.getUrl()).isNotBlank());

    assertThat(dto.getFormats()).hasSize(2)
        .anySatisfy(format -> assertThat(format.getDescriptions())
            .containsExactly("description-one", "description-two"))
        .anySatisfy(format -> assertThat(format.getDescriptions()).isEmpty());
  }

  @Test
  void findAllByAppliesFiltersPaginationAndSafeSorting() {
    saveRelease(1L, "Alpha", "US", LocalDate.of(2020, 5, 1), true, true, true);
    saveRelease(2L, "Beta", "US", LocalDate.of(2020, 6, 2), true, true, false);
    saveRelease(3L, "Alpha Remix", "KR", LocalDate.of(2021, 5, 3), true, true, false);
    saveRelease(4L, "Hidden Year", "US", LocalDate.of(2020, 5, 4), false, true, true);

    var filtered = repository.findAllBy(
        new ReleaseQuery("alpha", "us", 2020, 5, true),
        PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")))).block();

    assertThat(filtered).isNotNull();
    assertThat(filtered.getTotalElements()).isEqualTo(1);
    assertThat(filtered.getContent()).extracting("id").containsExactly(1L);

    var firstPage = repository.findAllBy(
        ReleaseQuery.builder().build(),
        PageRequest.of(0, 2)).block();
    assertThat(firstPage).isNotNull();
    assertThat(firstPage.getTotalElements()).isEqualTo(4);
    assertThat(firstPage.getContent()).extracting("id").containsExactly(1L, 2L);

    for (String property : List.of(
        "id", "title", "country", "released_year", "released_month", "unsupported")) {
      var page = repository.findAllBy(
          ReleaseQuery.builder().build(),
          PageRequest.of(0, 10, Sort.by(Sort.Order.asc(property)))).block();
      assertThat(page).isNotNull();
      assertThat(page.getContent()).hasSize(4);
    }
  }

  private void saveRelease(long id, String title, String country, LocalDate releaseDate,
      boolean hasValidYear, boolean hasValidMonth, boolean isMaster) {
    var release = Release.builder()
        .id(id)
        .title(title)
        .country(country)
        .dataQuality("Correct")
        .releaseDate(releaseDate)
        .hasValidYear(hasValidYear)
        .hasValidMonth(hasValidMonth)
        .hasValidDay(true)
        .listedReleaseDate(releaseDate.toString())
        .isMaster(isMaster)
        .notes("notes")
        .status("Accepted")
        .build();
    release.setAsNew();
    delegate.save(release).block();
  }

  private void prepareFormats() {
    client.sql("""
            INSERT INTO release_item_format
              (created_at, last_modified_at, hash, name, quantity, description, release_item_id)
            VALUES
              (NOW(), NOW(), 1, 'Vinyl', 1, 'description-one,description-two', 1),
              (NOW(), NOW(), 2, 'File', 1, NULL, 1)
            """)
        .then()
        .block();
  }

  @NotNull
  private List<String> prepareVideos() {
    return IntStream.rangeClosed(1, 5)
        .mapToObj(i -> TestUtil.getRandomString())
        .peek(url -> client.sql(
                """
                    INSERT INTO release_item_video
                      (created_at, last_modified_at, hash, description, title, url, release_item_id)
                    VALUES (NOW(), NOW(), :hash, 'test_description', 'test_title', :url, 1)
                    """)
            .bind("hash", url.hashCode())
            .bind("url", url).then().block())
        .toList();
  }

  @NotNull
  private List<String> prepareStyles() {
    var styles = IntStream.rangeClosed(1, 3)
        .mapToObj(id -> TestUtil.getRandomString())
        .peek(style -> client.sql("INSERT INTO style (name) VALUES (:name)")
            .bind("name", style)
            .then()
            .block())
        .toList();
    styles.forEach(style -> client.sql(
            """
                INSERT INTO release_item_style
                  (created_at, last_modified_at, release_item_id, style)
                VALUES (NOW(), NOW(), 1, :style)
                """)
        .bind("style", style)
        .then()
        .block());
    return styles;
  }

  @NotNull
  private List<String> prepareGenres() {
    var genres = IntStream.rangeClosed(1, 3)
        .mapToObj(id -> TestUtil.getRandomString())
        .peek(genre -> client.sql("INSERT INTO genre (name) VALUES (:name)")
            .bind("name", genre)
            .then()
            .block())
        .toList();
    genres.forEach(genre -> client.sql(
            """
                INSERT INTO release_item_genre
                  (created_at, last_modified_at, genre, release_item_id)
                VALUES (NOW(), NOW(), :genre, 1)
                """)
        .bind("genre", genre)
        .then()
        .block());
    return genres;
  }

  private List<Label> prepareLabels() {
    List<Label> labels = new ArrayList<>();
    var label = TestUtil.getInstanceOf(Label.class).withId(1L);
    var company = TestUtil.getInstanceOf(Label.class).withId(2L);
    label.setAsNew();
    company.setAsNew();
    labelR2dbcRepository.save(label).block();
    labelR2dbcRepository.save(company).block();
    client.sql(
            """
                INSERT INTO label_release_item
                  (created_at, last_modified_at, category_notation, label_id, release_item_id)
                VALUES (NOW(), NOW(), 'test_category_notation', 1, 1)
                """)
        .then()
        .block();
    client.sql(
            """
                INSERT INTO release_item_work
                  (created_at, last_modified_at, hash, work, label_id, release_item_id)
                VALUES (NOW(), NOW(), 3, 'test_contract', 2, 1)
                """)
        .then()
        .block();
    labels.add(label);
    labels.add(company);
    return labels;
  }

  private Master prepareMaster() {
    var master = getEntityOf(Master.class).withReleasedYear(2009);
    master.setAsNew();
    masterR2dbcRepository.save(master).block();
    assertThat(master).isNotNull();
    return master;
  }

  private List<Artist> prepareArtists() {
    var artists = getItems(Artist.class, 3);
    for (int i = 0; i < artists.size(); i++) {
      artists.set(i, artists.get(i).withId((long) i + 1));
      artists.get(i).setAsNew();
    }
    artistR2dbcRepository.saveAll(artists).collectList().block();
    assertThat(artists).isNotNull();
    for (Artist artist : artists) {
      assertThat(artist.getId()).isNotNull();
      client.sql(
              """
                  INSERT INTO release_item_artist
                    (created_at, last_modified_at, artist_id, release_item_id)
                  VALUES (NOW(), NOW(), :id, 1)
                  """)
          .bind("id", artist.getId()).then()
          .block();
      if (artist == artists.get(2)) {
        client.sql(
                """
                    INSERT INTO release_item_credited_artist
                      (created_at, last_modified_at, hash, role, artist_id, release_item_id)
                    VALUES (NOW(), NOW(), 333, 'test_role', :id, 1)
                    """)
            .bind("id", artist.getId()).then().block();
      }
    }
    return artists;
  }

  private <T extends BaseEntity<ID>, ID> T getEntityOf(Class<T> clazz) {
    var entity = TestUtil.getInstanceOf(clazz);
    entity.setAsNew();
    return entity;
  }

  private <T extends BaseEntity<ID>, ID> List<T> getItems(Class<T> clazz, int count) {
    return IntStream.rangeClosed(1, count).mapToObj(i -> getEntityOf(clazz))
        .collect(Collectors.toList());
  }
}
