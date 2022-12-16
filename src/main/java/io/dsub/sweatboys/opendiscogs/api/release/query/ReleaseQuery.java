package io.dsub.sweatboys.opendiscogs.api.release.query;

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
) {}
