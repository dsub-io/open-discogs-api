package io.dsub.sweatboys.opendiscogs.api.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.With;

// It seems like need to get release data & artist as subquery.

@With
public record LabelReleaseDTO(
        @JsonProperty("id")
        Long id,
        @JsonProperty("artist")
        String artist,
        @JsonProperty("title")
        String title,
        @JsonProperty("released_year")
        Integer releasedYear,
        @JsonProperty("status")
        String status,
        @JsonProperty("catno")
        String categoryNotation,
        @JsonProperty("format")
        String description
) {}
