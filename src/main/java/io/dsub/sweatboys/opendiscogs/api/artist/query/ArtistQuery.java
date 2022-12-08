package io.dsub.sweatboys.opendiscogs.api.artist.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import lombok.Builder;

@Builder
public record ArtistQuery(
    @JsonProperty("name")
    String name,
    @JsonProperty("real_name")
    String realName,
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
