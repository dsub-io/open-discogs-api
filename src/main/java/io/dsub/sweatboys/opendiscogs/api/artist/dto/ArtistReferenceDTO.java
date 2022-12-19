package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import static io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfig.getServerAddress;

public record ArtistReferenceDTO(Long id, String name) {

  public String getResourceURL() {
    return getServerAddress() + "/artists/" + id;
  }
}
