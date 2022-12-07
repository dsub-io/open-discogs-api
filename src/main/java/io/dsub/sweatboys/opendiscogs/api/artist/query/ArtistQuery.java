package io.dsub.sweatboys.opendiscogs.api.artist.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.util.StringUtility;
import lombok.Builder;

@Builder
public record ArtistQuery(
    @JsonProperty("name")
    String name,
    @JsonProperty("realName")
    String realName,
    @JsonProperty("profile")
    String profile
) {
    public Artist toArtist() {
        return Artist.builder()
            .name(StringUtility.getInstance().normalize(name))
            .realName(StringUtility.getInstance().normalize(realName))
            .profile(StringUtility.getInstance().normalize(profile))
            .build();
    }
}
