package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistDetailDTO;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.FluentQuery.ReactiveFluentQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {

  public static final String SEL_ARTIST_MEMBERS_SQL = ""
      + "SELECT id, name FROM artist "
      + "LEFT JOIN artist_group ag on artist.id = ag.group_id "
      + "WHERE artist_id = :id";

  private final ArtistR2dbcRepository delegate;
  @Override
  public Mono<Page<Artist>> findAllBy(Example<Artist> example, Pageable pageable) {
    return delegate.findBy(example, getPageableQueryFunction(pageable));
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

  private static Function<Artist, Mono<? extends ArtistDetailDTO>> toDTO() {
    return ArtistDetailDTO::fromArtist;
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistMembers() {
    return dto -> delegate.findMemberArtists(dto.getId()).collectList().map(dto::withMembers);
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistGroups() {
    return dto -> delegate.findGroupArtists(dto.getId()).collectList().map(dto::withGroups);
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withArtistAliases() {
    return dto -> delegate.findAliasArtists(dto.getId()).collectList().map(dto::withAliases);
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withUrls() {
    return dto -> delegate.findUrls(dto.getId()).collectList().map(dto::withUrls);
  }

  private Function<ArtistDetailDTO, Mono<? extends ArtistDetailDTO>> withNameVariations() {
    return dto -> delegate.findNameVariations(dto.getId()).collectList().map(dto::withNameVariations);
  }

  private static Function<ReactiveFluentQuery<Artist>, Mono<Page<Artist>>> getPageableQueryFunction(
      Pageable pageable) {
    return p -> p.as(Artist.class).page(pageable);
  }
}
