package io.dsub.sweatboys.opendiscogs.api.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.With;
import org.springframework.data.relational.core.mapping.Column;

@With
public record LabelReleaseDTO(
    @JsonProperty("id")
    Long id,
    @JsonProperty("artist")
    @Column("artist")
    String name,
    @JsonProperty("title")
    @Column("title")
    String title,
    @JsonProperty("year")
    @Column("year")
    Integer releasedYear,
    @JsonProperty("status")
    @Column("status")
    String status,
    @JsonProperty("catno")
    @Column("catno")
    @Nullable
    String categoryNotation,
    @JsonProperty("format")
    @Column("format")
    String description
) {

}
