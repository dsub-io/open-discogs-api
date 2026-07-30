package io.dsub.opendiscogs.api.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@NoArgsConstructor
public class DatabaseProperties {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String host;
  @NotBlank
  private String database = "discogs";

  public String getUrl() {
    return "r2dbc:postgresql://%s/%s".formatted(host, database);
  }
}
