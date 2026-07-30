package io.dsub.opendiscogs.api.release.query;

import io.dsub.opendiscogs.api.core.util.StringUtility;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record ReleaseQuery(
    String title,
    String country,
    Integer year,
    Integer month,
    Boolean isMaster
) {

  public ReleaseQuery normalized() {
    return new ReleaseQuery(
        StringUtility.normalize(title),
        StringUtility.normalize(country),
        year,
        month,
        isMaster);
  }
}
