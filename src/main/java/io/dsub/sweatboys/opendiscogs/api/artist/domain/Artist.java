package io.dsub.sweatboys.opendiscogs.api.artist.domain;

import io.dsub.sweatboys.opendiscogs.api.core.entity.PersistableBaseEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artist")
public class Artist extends PersistableBaseEntity<Long> {

  @Id
  @NotNull
  @Min(1)
  @Column("id")
  private Long id;

  @Column("name")
  private String name;

  @Column("real_name")
  private String realName;

  @Column("profile")
  private String profile;

  @Column("data_quality")
  private String dataQuality;
}
