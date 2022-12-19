package io.dsub.sweatboys.opendiscogs.api.release.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import static io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfig.getServerAddress;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ReleaseLabelDTO {
    @Column("id")
    @JsonProperty("id")
    private Long id;
    @Column("name")
    @JsonProperty("name")
    private String name;
    @Column("category_notation")
    @JsonProperty("catno")
    private String categoryNotation;

    @JsonProperty("resource_url")
    public String getResourceUrl() {
        return getServerAddress() + "/label/" + this.id;
    }
}
