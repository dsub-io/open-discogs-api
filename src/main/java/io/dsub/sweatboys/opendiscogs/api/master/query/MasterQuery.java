package io.dsub.sweatboys.opendiscogs.api.master.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterQuery {
  @Schema(description = "Year when master released")
  @Builder.Default
  private final Integer year = null;
  @Schema(description = "Title of master release")
  @Builder.Default
  private final String title = null;
}
