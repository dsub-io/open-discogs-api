package io.dsub.sweatboys.opendiscogs.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static io.dsub.sweatboys.opendiscogs.api.config.ApplicationPropertiesConfiguration.getServerUrl;

@Configuration
public class SwaggerConfiguration {
    private static final String VERSION = "1.5.4";
    @Bean
    public OpenAPI getOpenAPI() {
        var api = new OpenAPI();
        var server = new Server();
        server.description("Open Discogs API Documentation");
        server.setUrl("/");
        var info = new Info();
        // {x-release-please-start-version}
        info.setVersion(VERSION);
        // {x-release-please-end}
        info.setTitle("Open Discogs API");
        info.setDescription("Open Discogs API Documentation");
        api.setServers(List.of(server));
        api.setInfo(info);
        return api;
    }
}
