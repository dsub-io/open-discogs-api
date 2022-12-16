package io.dsub.sweatboys.opendiscogs.api.master.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "master")
@AllArgsConstructor
public class Master {
    @Id
    @Min(1)
    @Column("id")
    @JsonProperty("id")
    private final Long id;

    @Column("data_quality")
    @JsonProperty("data_quality")
    private final String dataQuality;

    @Column("title")
    @JsonProperty("title")
    private final String title;

    @Column("released_year")
    @JsonProperty("released_year")
    private final Integer releasedYear;
}
