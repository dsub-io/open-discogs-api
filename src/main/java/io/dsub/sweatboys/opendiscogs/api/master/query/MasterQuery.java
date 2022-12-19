package io.dsub.sweatboys.opendiscogs.api.master.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@With
@Getter
@Builder
@AllArgsConstructor
public class MasterQuery {
  @Schema(description = "Year when master released")
  @Builder.Default
  private final Integer year = null;
  @Schema(description = "Title of master release")
  @Builder.Default
  private final String title = null;
}
