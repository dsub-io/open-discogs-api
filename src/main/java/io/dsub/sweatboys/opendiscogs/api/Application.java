package io.dsub.sweatboys.opendiscogs.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder.SpringWebBlockHoundIntegration;

@SpringBootApplication
// {x-release-please-start-version}
@OpenAPIDefinition(info = @Info(title = "Open Discogs API", version = "0.0.0", description = "Documentation APIs v0.0.0"))
// {x-release-please-end}
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
