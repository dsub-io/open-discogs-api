package io.dsub.sweatboys.opendiscogs.api.release.dto;

import static io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfig.getServerAddress;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.relational.core.mapping.Column;

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
