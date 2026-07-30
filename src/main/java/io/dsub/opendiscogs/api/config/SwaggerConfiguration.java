package io.dsub.opendiscogs.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SwaggerConfiguration {

  private final BuildProperties buildProperties;

  @Bean
  public OpenAPI openApi() {
    var server = new Server()
        .description("OpenDiscogs API")
        .url(ApplicationPropertiesConfiguration.getServerUrl());
    var info = new Info()
        .version(buildProperties.getVersion())
        .title("OpenDiscogs API")
        .description("Read-only API for an independently imported copy of public Discogs data.");
    return new OpenAPI()
        .servers(List.of(server))
        .info(info);
  }
}
