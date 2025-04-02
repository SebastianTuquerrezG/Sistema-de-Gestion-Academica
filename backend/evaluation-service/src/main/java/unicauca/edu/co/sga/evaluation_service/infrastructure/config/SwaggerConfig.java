package unicauca.edu.co.sga.evaluation_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.permanentRedirect;

@Configuration
public class SwaggerConfig {
    @Bean
    public RouterFunction
            <ServerResponse> swaggerUIResources() {
        return route(GET("/swagger-ui/**"), request ->
                ok().bodyValue("Swagger UI resources"))
                .andRoute(GET("/swagger-ui.html"), request ->
                        permanentRedirect(URI.create("/swagger-ui/index.html")).build());
    }
}
