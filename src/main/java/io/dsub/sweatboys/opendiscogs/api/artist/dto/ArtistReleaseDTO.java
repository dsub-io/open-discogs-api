package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfig;
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
  @JsonProperty("master_id")
  private Long masterId;
  @JsonProperty("notes")
  private String notes;
  @JsonProperty("status")
  private String status;

  @JsonProperty("resource_url")
  public String getResourceUrl() {
    return ApplicationPropertiesConfig.getServerAddress() + "/releases/" + id;
  }
}
