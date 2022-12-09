package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Detailed information of an artist")
public class ArtistDetailDTO {
  private final Long id;
  private final String name;
  @JsonProperty("real_name")
  private final String realName;
  private final String profile;
  @JsonProperty("data_quality")
  private final String dataQuality;
  private final List<ArtistReferenceDTO> members;
  private final List<ArtistReferenceDTO> groups;
  private final List<ArtistReferenceDTO> aliases;
  @JsonProperty("namevariations")
  private final List<String> nameVariations;
  private final List<String> urls;

  public String getUri() {
    return "https://www.discogs.com/artist/" + this.id;
  }

  public String getReleasesUrl() {
    return "https://api.opendiscogs.com/artists/" + this.id + "/releases";
  }

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
