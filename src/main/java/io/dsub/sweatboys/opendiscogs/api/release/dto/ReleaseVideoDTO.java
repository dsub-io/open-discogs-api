package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public final class ReleaseVideoDTO {
  @JsonProperty("title")
  private String title;
  @JsonProperty("url")
  private String url;
  @JsonProperty("description")
  private String description;
}
