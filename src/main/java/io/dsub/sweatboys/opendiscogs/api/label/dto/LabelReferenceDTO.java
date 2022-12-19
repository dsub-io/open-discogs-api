package io.dsub.sweatboys.opendiscogs.api.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * @param id   Id to search for label.
 * @param name Name to search for name.
 */
@Builder
public record LabelReferenceDTO(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name) {

  @JsonProperty("resource_url")
  public String getResourceURL() {
    return "https://api.opendiscogs.com/labels/" + id;
  }
}
