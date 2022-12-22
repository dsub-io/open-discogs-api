package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseFormatDTO {

  @JsonProperty("name")
  private String name;
  @JsonProperty("qty")
  private Integer quantity;
  @JsonProperty("descriptions")
  private List<String> descriptions;
}
