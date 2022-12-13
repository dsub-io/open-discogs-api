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
  @JsonProperty("id")
  private final Long id;
  @JsonProperty("name")
  private final String name;
  @JsonProperty("real_name")
  private final String realName;
  @JsonProperty("profile")
  private final String profile;
  @JsonProperty("data_quality")
  private final String dataQuality;
  @JsonProperty("members")
  private final List<ArtistReferenceDTO> members;
  @JsonProperty("groups")
  private final List<ArtistReferenceDTO> groups;
  @JsonProperty("aliases")
  private final List<ArtistReferenceDTO> aliases;
  @JsonProperty("namevariations")
  private final List<String> nameVariations;
  @JsonProperty("urls")
  private final List<String> urls;

  @JsonProperty("uri")
  public String getUri() {
    return "https://www.discogs.com/artist/" + this.id;
  }

  @JsonProperty("release_url")
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
