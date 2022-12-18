package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistReleaseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("role")
    private String role;
    @JsonProperty("title")
    private String title;
    @JsonProperty("country")
    private String country;
    @JsonProperty("data_quality")
    private String dataQuality;
    @JsonProperty("released_year")
    private Integer releasedYear;
    @JsonProperty("released_month")
    private Integer releasedMonth;
    @JsonProperty("released_day")
    private Integer releasedDay;
    @JsonProperty("listed_release_date")
    private String listedReleaseDate;
    @JsonProperty("is_master")
    private Boolean isMaster;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("status")
    private String status;
}
