package io.dsub.sweatboys.opendiscogs.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Open Discogs API",
        // {x-release-please-start-version}
        version = "1.3.1", description =
        // {x-release-please-end}
        "Open Discogs API Documentation"
))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
