package io.dsub.sweatboys.opendiscogs.api.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MasterVideoDTO(
    @JsonProperty("url")
    String url,
    @JsonProperty("description")
    String description,
    @JsonProperty("title")
    String title
) {

}
