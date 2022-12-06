package io.dsub.sweatboys.opendiscogs.api.artist.service;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import io.dsub.sweatboys.opendiscogs.api.artist.query.ArtistQuery;
import io.dsub.sweatboys.opendiscogs.api.core.response.ResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.service.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ArtistService implements PagingService {

  private final ArtistRepository artistRepository;

  public Mono<ResponseDTO<Artist>> findArtists(ArtistQuery query, Pageable pageable) {
    return artistRepository.findAllBy(Example.of(query.toArtist(),
            ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()), pageable)
        .collectList()
        .flatMap(list -> Mono.just(ResponseDTO.<Artist>builder()
            .items(list)
            .build()))
        .subscribeOn(Schedulers.boundedElastic());
  }
}
