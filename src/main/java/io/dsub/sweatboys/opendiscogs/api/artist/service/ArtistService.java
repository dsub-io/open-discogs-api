package io.dsub.sweatboys.opendiscogs.api.artist.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.response.ResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.service.PagingService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArtistService implements PagingService {

  private final ArtistRepository artistRepository;

  public Mono<ResponseDTO<Artist>> findArtists(ArtistQuery query, Pageable pageable) {
    return artistRepository.findAllBy(Example.of(query.toArtist(),
        matchingAll()
            .withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnoreNullValues()), pageable)
        .flatMap(fromPageToResponseDTO());
  }

  private Function<Page<Artist>, Mono<ResponseDTO<Artist>>> fromPageToResponseDTO() {
    return ResponseDTO::fromPage;
  }

  public Mono<ResponseDTO<Artist>> findArtist(long id) {
    return artistRepository.findById(Example.of(Artist.builder().id(id).build()),
            PageRequest.of(0, 1))
        .flatMap(fromPageToResponseDTO());
  }
}
