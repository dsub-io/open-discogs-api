package io.dsub.sweatboys.opendiscogs.api.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDTO(
    @JsonProperty("reason")
    String reason,
    @JsonProperty("rejected")
    String rejected,
    @JsonProperty("param")
    String param,
    @JsonProperty("resource_uri")
    String resourceURI) {}