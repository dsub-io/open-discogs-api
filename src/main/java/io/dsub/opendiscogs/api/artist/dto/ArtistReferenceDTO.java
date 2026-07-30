package io.dsub.opendiscogs.api.artist.dto;

import static io.dsub.opendiscogs.api.config.ApplicationPropertiesConfiguration.getServerUrl;

public record ArtistReferenceDTO(Long id, String name) {

  public String getResourceURL() {
    return getServerUrl() + "/artists/" + id;
  }
}
