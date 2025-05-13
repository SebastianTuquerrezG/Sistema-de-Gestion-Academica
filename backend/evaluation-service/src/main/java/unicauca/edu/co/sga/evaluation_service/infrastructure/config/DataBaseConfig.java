package unicauca.edu.co.sga.evaluation_service.infrastructure.config;

import org.springframework.core.env.Environment;

public class DataBaseConfig {
    private final Environment environment;

    public DataBaseConfig(Environment environment){
        this.environment = environment;
    }

    public String getConfigValue(String key){
        return environment.getProperty(key);
    }
}
