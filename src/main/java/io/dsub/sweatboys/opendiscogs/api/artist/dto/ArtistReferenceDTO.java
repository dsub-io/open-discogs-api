package io.dsub.sweatboys.opendiscogs.api.artist.dto;

public record ArtistReferenceDTO(Long id, String name) {
  public String getResourceURL() {
    return "https://api.opendiscogs.com/artists/" + id;
  }
}
