package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.With;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@With
@Builder
@Schema(description = "Detailed information of an artist")
public record ArtistDetailDTO(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                              @JsonProperty("real_name") String realName,
                              @JsonProperty("profile") String profile,
                              @JsonProperty("data_quality") String dataQuality,
                              @JsonProperty("members") List<ArtistReferenceDTO> members,
                              @JsonProperty("groups") List<ArtistReferenceDTO> groups,
                              @JsonProperty("aliases") List<ArtistReferenceDTO> aliases,
                              @JsonProperty("namevariations") List<String> nameVariations,
                              @JsonProperty("urls") List<String> urls) {

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
