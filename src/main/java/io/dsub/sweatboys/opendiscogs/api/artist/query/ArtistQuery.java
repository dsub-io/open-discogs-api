package io.dsub.sweatboys.opendiscogs.api.artist.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Mapped query object for artists query.
 * @param name Name to search for artist
 * @param realName Real name to search for artist
 * @param profile Profile to search for artist
 */
@Builder
public record ArtistQuery(
    @Schema(description = "Name to search for artist", defaultValue = "")
    @JsonProperty("name")
    String name,

    @Schema(description = "Real name to search for artist", defaultValue = "")
    @JsonProperty("realName")
    String realName,

    @Schema(description = "Profile to search for artist", defaultValue = "")
    @JsonProperty("profile")
    String profile
) {
    public Artist toArtist() {
        return Artist.builder()
            .name(ofNormalized(name))
            .realName(ofNormalized(realName))
            .profile(ofNormalized(profile))
            .build();
    }

    private String ofNormalized(String in) {
        return StringUtility.getInstance().normalize(in);
    }
}
