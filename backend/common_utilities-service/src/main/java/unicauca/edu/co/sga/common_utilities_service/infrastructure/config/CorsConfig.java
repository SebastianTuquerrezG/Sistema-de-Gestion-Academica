package unicauca.edu.co.sga.common_utilities_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Collections.singletonList("*"));

        config.setAllowedMethods(Collections.singletonList("*"));

        config.setAllowedHeaders(Collections.singletonList("*"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
