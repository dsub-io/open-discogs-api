package io.dsub.sweatboys.opendiscogs.api.artist.query;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import lombok.Builder;

/**
 * Mapped query object for artists query.
 * @param name Name to search for artist
 * @param realName Real name to search for artist
 * @param profile Profile to search for artist
 */
@Builder
public record ArtistQuery(
    String name,
    String realName,
    String profile) {
    public Artist toArtist() {
        return Artist.builder()
            .name(ofNormalized(name))
            .realName(ofNormalized(realName))
            .profile(ofNormalized(profile))
            .build();
    }

    private String ofNormalized(String in) {
        return StringUtility.normalize(in);
    }
}
