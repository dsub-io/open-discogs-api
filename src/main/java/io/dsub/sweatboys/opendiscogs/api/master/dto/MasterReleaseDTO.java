package io.dsub.sweatboys.opendiscogs.api.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

/**
 * Represents releases as resources from masters' view
 *
 * @param releaseId id of the release
 * @param title     title of the release
 * @param artist    artist name of the release
 * @param artistId  artist id of the release
 * @param year      released year of the release
 */
@With
@Builder
public record MasterReleaseDTO(
        @JsonProperty("id")
        @Column("id")
        Long releaseId,
        @JsonProperty("title")
        String title,
        @JsonProperty("artist")
        List<String> artist,
        @JsonProperty("artist_id")
        List<Long> artistId,
        @JsonProperty("year")
        Long year
) {
    @JsonProperty("resource_url")
    public String getResourceUrl() {
        return "https://api.opendiscogs.com/releases/" + releaseId;
    }
}
