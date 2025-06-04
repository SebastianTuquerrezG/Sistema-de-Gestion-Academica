package com.nullco.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter addResponseHeaderFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.defer(() -> {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("X-Response-Time", Instant.now().toString());
                return Mono.empty();
            }));
        };
    }
}