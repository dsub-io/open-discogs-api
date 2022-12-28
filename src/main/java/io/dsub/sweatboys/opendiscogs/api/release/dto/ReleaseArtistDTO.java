package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfiguration;
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
public class ReleaseArtistDTO {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("role")
  private String role;

  @JsonProperty("resource_url")
  public String getResourceUrl() {
    return ApplicationPropertiesConfiguration.getServerUrl() + "/artists/" + id;
  }
}
