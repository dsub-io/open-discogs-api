package io.dsub.sweatboys.opendiscogs.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(info = @Info(
        title = "Open Discogs API",
        // {x-release-please-start-version}
        version = "1.2.1", description =
        // {x-release-please-end}
        "Open Discogs API Swagger: " +
        // {x-release-please-start-version}
        "1.1.0"
        // {x-release-please-end}
))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
