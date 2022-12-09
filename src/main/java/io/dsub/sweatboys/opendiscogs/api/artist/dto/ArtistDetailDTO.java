package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@With
@Getter
@Builder
@AllArgsConstructor
public class ArtistDetailDTO {
  private final Long id;
  private final String name;
  private final String realName;
  private final String profile;
  private final String dataQuality;
  private final List<ArtistReferenceDTO> members;
  private final List<ArtistReferenceDTO> groups;
  private final List<ArtistReferenceDTO> aliases;
  private final List<String> nameVariations;
  private final List<String> urls;

  public static Mono<ArtistDetailDTO> fromArtist(Artist artist) {
    return Mono.fromCallable(() -> ArtistDetailDTO.builder()
            .id(artist.getId())
            .name(artist.getName())
            .realName(artist.getRealName())
            .profile(artist.getProfile())
            .dataQuality(artist.getDataQuality())
        .build())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
