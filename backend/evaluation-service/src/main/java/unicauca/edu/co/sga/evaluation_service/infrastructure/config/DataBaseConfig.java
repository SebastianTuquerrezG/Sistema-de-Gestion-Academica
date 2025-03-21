package unicauca.edu.co.sga.evaluation_service.infrastructure.config;

import org.springframework.core.env.Environment;

public class DataBaseConfig {
    // Maybe if you need to use and call the environment variables.
    private final Environment environment;

    public DataBaseConfig(Environment environment){
        this.environment = environment;
    }

    // With this function you can get all the keys that you need (Environment Variables)
    public String getConfigValue(String key){
        return environment.getProperty(key);
    }
}
