package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import lombok.*;

@With
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ReleaseDTO {
    @JsonProperty("id")
    private Long id;
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
    @JsonProperty("master_id")
    private Long masterId;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("status")
    private String status;
    public static ReleaseDTO fromRelease(Release release) {
        return ReleaseDTO.builder()
                .id(release.getId())
                .title(release.getTitle())
                .country(release.getCountry())
                .dataQuality(release.getDataQuality())
                .releasedYear(release.getReleasedYear())
                .releasedMonth(release.getReleasedMonth())
                .releasedDay(release.getReleasedDay())
                .listedReleaseDate(release.getListedReleaseDate())
                .isMaster(release.getIsMaster())
                .notes(release.getNotes())
                .status(release.getStatus())
                .masterId(release.getId())
                .build();
    }
}
