package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import static io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfiguration.getServerUrl;

public record ArtistReferenceDTO(Long id, String name) {

  public String getResourceURL() {
    return getServerUrl() + "/artists/" + id;
  }
}
