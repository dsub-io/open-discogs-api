package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseFormatDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("qty")
    private Integer quantity;
    @JsonProperty("descriptions")
    private List<String> descriptions;
}
