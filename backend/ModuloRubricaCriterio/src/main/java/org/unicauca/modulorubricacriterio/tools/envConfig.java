package org.unicauca.modulorubricacriterio.tools;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class envConfig {
    static {
        try {
            Path projectRoot = Paths.get("").toAbsolutePath()
                    .getParent();
            Path envPath = projectRoot.resolve(".env");

            System.out.println("Cargando archivo .env desde: " + envPath);

            Dotenv dotenv = Dotenv.configure()
                    .directory(projectRoot.toString())
                    .load();

            dotenv.entries().forEach(entry ->
                    System.setProperty(entry.getKey(), entry.getValue())
            );

        } catch (Exception e) {
            System.err.println("Error cargando el archivo .env: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

