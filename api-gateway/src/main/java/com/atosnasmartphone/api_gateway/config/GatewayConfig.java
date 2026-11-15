package com.atosnasmartphone.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-route", r -> r
                        .path("/product/**")
                        .uri("lb://product-service"))
                .route("scheduler-route", r -> r
                        .path("/scheduler/**")
                        .filters(f -> f.addRequestHeader("X-Source", "gateway"))
                        .uri("lb://scheduler-service"))
                .route("auth-route", r -> r
                        .path("/auth/**")
                        .filters(f -> f.addRequestHeader("X-Source", "gateway"))
                        .uri("lb://auth-service"))
                .build();
    }
}
