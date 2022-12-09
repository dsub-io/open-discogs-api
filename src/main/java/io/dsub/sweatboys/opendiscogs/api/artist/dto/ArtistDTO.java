package io.dsub.sweatboys.opendiscogs.api.artist.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ArtistDTO(Long id,
                        String name,
                        String realName,
                        String profile,
                        String dataQuality) {}
