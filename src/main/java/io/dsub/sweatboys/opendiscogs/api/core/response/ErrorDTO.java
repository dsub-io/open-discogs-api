package io.dsub.sweatboys.opendiscogs.api.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorDTO(
    @JsonProperty("reason")
    String reason,
    @JsonProperty("rejected")
    Object rejected,
    @JsonProperty("param")
    String param
) {}