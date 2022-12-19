package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseDetailDTO {

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
  @JsonProperty("artists")
  private List<ReleaseArtistDTO> artists;
  @JsonProperty("labels")
  private List<ReleaseLabelDTO> labels;
  @JsonProperty("companies")
  private List<ReleaseLabelDTO> companies;
  @JsonProperty("formats")
  private List<ReleaseFormatDTO> formats;
  @JsonProperty("styles")
  private List<String> styles;
  @JsonProperty("genres")
  private List<String> genres;
  @JsonProperty("videos")
  private List<ReleaseVideoDTO> videos;

  public static ReleaseDetailDTO fromRelease(Release release) {
    return ReleaseDetailDTO.builder()
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
