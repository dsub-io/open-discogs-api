package io.dsub.sweatboys.opendiscogs.api.release.infrastructure;


import io.dsub.opendiscogs.jooq.tables.pojos.Genre;
import io.dsub.opendiscogs.jooq.tables.pojos.Style;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.dsub.sweatboys.opendiscogs.api.label.infrastructure.LabelR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.infrastructure.MasterR2dbcRepository;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

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
                .withReleasedYear(2010)
                .withReleasedMonth(3)
                .withReleasedDay(12);
        release.setAsNew();
        delegate.save(release).block();

        var artists = prepareArtists();
        var labels = prepareLabels();
        var genres = prepareGenres();
        var styles = prepareStyles();
        var urls = prepareVideos();
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
        assertThat(label.getCategoryNotation()).isEqualTo("Label");
        assertThat(label.getName()).isEqualTo(labels.get(0).getName());
        assertThat(label.getId()).isEqualTo(1);

        assertThat(dto.getCompanies()).hasSize(1);
        var company = dto.getCompanies().get(0);
        assertThat(company).isNotNull();
        assertThat(company.getCategoryNotation()).isEqualTo("test_contract");
        assertThat(company.getName()).isEqualTo(labels.get(1).getName());
        assertThat(company.getId()).isEqualTo(2);

        var expectedGenres = genres.stream().map(Genre::getName).toList();
        var expectedStyles = styles.stream().map(Style::getName).toList();
        assertThat(dto.getGenres().stream().distinct().collect(Collectors.toList()))
                .hasSize(3)
                .allSatisfy(genre -> assertThat(expectedGenres).contains(genre));
        assertThat(dto.getStyles().stream().distinct().collect(Collectors.toList()))
                .hasSize(3)
                .allSatisfy(style -> assertThat(expectedStyles).contains(style));

        assertThat(dto.getVideos()).hasSize(5)
                .allSatisfy(vid -> assertThat(vid.getTitle()).isEqualTo("test_title"))
                .allSatisfy(vid -> assertThat(vid.getDescription()).isEqualTo("test_description"))
                .allSatisfy(vid -> assertThat(vid.getUrl()).isNotBlank());

        assertThat(dto.getFormats()).isNotNull().isEmpty();
    }

    @NotNull
    private List<String> prepareVideos() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(i -> TestUtil.getRandomString())
                .peek(url -> client.sql("INSERT INTO release_video VALUES (1, 'test_description', 'test_title', :url, :hash)")
                        .bind("hash", url.hashCode())
                        .bind("url", url).then().block())
                .toList();
    }

    @NotNull
    private List<Style> prepareStyles() {
        var styles = IntStream.rangeClosed(1, 3)
                .mapToObj(id -> new Style(id, TestUtil.getRandomString()))
                .peek(style -> client.sql("INSERT INTO style VALUES (:id, :name)")
                        .bind("id", style.getId())
                        .bind("name", style.getName())
                        .then()
                        .block())
                .toList();
        client.sql("INSERT INTO release_style VALUES (1,1),(1,2),(1,3)").then().block();
        return styles;
    }

    @NotNull
    private List<Genre> prepareGenres() {
        var genres = IntStream.rangeClosed(1, 3)
                .mapToObj(id -> new Genre(id, TestUtil.getRandomString()))
                .peek(genre -> client.sql("INSERT INTO genre values (:id, :name)")
                        .bind("id", genre.getId())
                        .bind("name", genre.getName())
                        .then()
                        .block())
                .toList();
        client.sql("INSERT INTO release_genre VALUES (1,1),(1,2),(1,3)").then().block();
        return genres;
    }

    private List<Label> prepareLabels() {
        List<Label> labels = new ArrayList<>();
        var label = TestUtil.getInstanceOf(Label.class).withParentId(null).withId(1L);
        var company = TestUtil.getInstanceOf(Label.class).withParentId(null).withId(2L);
        label.setAsNew();
        company.setAsNew();
        labelR2dbcRepository.save(label).block();
        labelR2dbcRepository.save(company).block();
        client.sql("INSERT INTO label_release VALUES (1, 1,'test_category_notation')").then().block();
        client.sql("INSERT INTO release_contract VALUES (1, 2, 3,'test_contract')").then().block();
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
            client.sql("INSERT INTO release_artist VALUES (1, :id)").bind("id", artist.getId()).then().block();
            if (artist == artists.get(2)) {
                client.sql("INSERT INTO release_credited_artist VALUES (1,:id,333,'test_role')").bind("id", artist.getId()).then().block();
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
        return IntStream.rangeClosed(1, count).mapToObj(i -> getEntityOf(clazz)).collect(Collectors.toList());
    }
}

