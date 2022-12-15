package io.dsub.sweatboys.opendiscogs.api.label.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.core.entity.BaseEntity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@With
@Getter
@Builder
@Table(name = "label")
@AllArgsConstructor
public class Label extends BaseEntity<Long> {
    @Id
    @Min(1)
    @Column("id")
    @JsonProperty("id")
    private final Long id;

    @Column("contact_info")
    @JsonProperty("contact_ifo")
    private final String contactInfo;

    @Column("data_quality")
    @JsonProperty("data_quality")
    private final String dataQuality;

    @Column("name")
    @JsonProperty("name")
    private final String name;

    @Column("profile")
    @JsonProperty("profile")
    private final String profile;

    @Column("parent_id")
    @JsonProperty("parent_id")
    private final Long parentId;
}
