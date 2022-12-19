package io.dsub.sweatboys.opendiscogs.api.release.query;

import io.dsub.sweatboys.opendiscogs.api.release.domain.Release;
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

  public Release toRelease() {
    return Release.builder()
        .title(title)
        .country(country)
        .releasedYear(year)
        .releasedMonth(month)
        .isMaster(isMaster)
        .build();
  }
}
