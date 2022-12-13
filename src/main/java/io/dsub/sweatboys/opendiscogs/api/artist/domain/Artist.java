package io.dsub.sweatboys.opendiscogs.api.artist.domain;

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
@Table(name = "artist")
@AllArgsConstructor
public class Artist extends BaseEntity<Long> {
  @Id
  @Min(1)
  @Column("id")
  @JsonProperty("id")
  private final Long id;

  @Column("name")
  @JsonProperty("name")
  private final String name;

  @Column("real_name")
  @JsonProperty("real_name")
  private final String realName;

  @Column("profile")
  @JsonProperty("profile")
  private final String profile;

  @Column("data_quality")
  @JsonProperty("data_quality")
  private final String dataQuality;
}
