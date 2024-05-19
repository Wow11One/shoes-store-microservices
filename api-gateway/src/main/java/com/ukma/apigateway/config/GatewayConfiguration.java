package com.ukma.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocator routes = builder.routes()
                .route(r -> r.path(
                        "/api/brands/**",
                                "/api/types/**",
                                "/api/orders/**",
                                "/api/shoes/**",
                                "/api/nova-post/**"
                                )
                        .uri("lb://shop-service")
                )
                .route(r -> r.path("/api/auth/**")
                        .uri("lb://authentication-service"))
                .build();
        System.out.println("custom route locator");
        routes.getRoutes().log();

        return routes;
    }
}
