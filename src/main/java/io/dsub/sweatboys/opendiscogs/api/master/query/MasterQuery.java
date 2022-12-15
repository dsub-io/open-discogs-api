package io.dsub.sweatboys.opendiscogs.api.master.query;

import io.swagger.v3.oas.annotations.media.Schema;

public record MasterQuery(
    @Schema(description = "Year when master released")
    Integer year,
    @Schema(description = "Title of master release")
    String title
) {}
