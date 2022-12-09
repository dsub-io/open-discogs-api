package io.dsub.sweatboys.opendiscogs.api.artist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.relational.core.mapping.Table;

@With
@Getter
@Builder
@Table(name = "artist_url")
@AllArgsConstructor
public class ArtistUrl {
  private Long id;
  private String url;
}
