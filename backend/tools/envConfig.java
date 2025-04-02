package com.nullco.rhservice.tools;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class envConfig {
    static {
        try {
            // Ruta absoluta al archivo .env en la raíz del proyecto (Backend)
            Path projectRoot = Paths.get("").toAbsolutePath() // Directorio actual (auth-service o rh-service)
                    .getParent(); // Subir un nivel para llegar a "Backend"
            Path envPath = projectRoot.resolve(".env");

            System.out.println("Cargando archivo .env desde: " + envPath);

            // Configurar dotenv para cargar las variables desde la ruta calculada
            Dotenv dotenv = Dotenv.configure()
                    .directory(projectRoot.toString())
                    .load();

            // Registrar cada variable como una propiedad del sistema
            dotenv.entries().forEach(entry ->
                    System.setProperty(entry.getKey(), entry.getValue())
            );

        } catch (Exception e) {
            System.err.println("Error cargando el archivo .env: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

