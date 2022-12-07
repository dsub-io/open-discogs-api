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
  @Min(1)
  @Column("id")
  @Builder.Default
  private Long id = null;

  @Column("name")
  @Builder.Default
  private String name = null;

  @Column("real_name")
  @Builder.Default
  private String realName = null;

  @Column("profile")
  @Builder.Default
  private String profile = null;

  @Column("data_quality")
  @Builder.Default
  private String dataQuality = null;
}
