package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistReferenceDTO(
    Long id,
    String name
) {
  @JsonProperty("resourceUrl")
  public String getResourceURL() {
    return "https://api.opendiscogs.com/artists/" + id;
  }
}
