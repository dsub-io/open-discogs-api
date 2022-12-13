package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.With;

@With
public record ArtistReleaseDTO(
    @JsonProperty("id")
    Long id,
    @JsonProperty("role")
    String role,
    @JsonProperty("title")
    String title,
    @JsonProperty("country")
    String country,
    @JsonProperty("data_quality")
    String dataQuality,
    @JsonProperty("released_year")
    Integer releasedYear,
    @JsonProperty("released_month")
    Integer releasedMonth,
    @JsonProperty("released_day")
    Integer releasedDay,
    @JsonProperty("listed_release_date")
    String listedReleaseDate,
    @JsonProperty("is_master")
    Boolean isMaster,
    @JsonProperty("notes")
    String notes,
    @JsonProperty("status")
    String status
) {}
