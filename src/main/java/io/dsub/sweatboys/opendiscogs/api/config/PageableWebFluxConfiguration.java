package io.dsub.sweatboys.opendiscogs.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration(proxyBeanMethods = false)
public class PageableWebFluxConfiguration extends WebFluxConfigurationSupport {
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        var resolver = new ReactivePageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        resolver.setMaxPageSize(30);
        configurer.addCustomResolver(resolver);
    }
}