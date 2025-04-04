package unicauca.edu.co.sga.evaluation_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
		exclude = {
				org.springframework.ai.autoconfigure.vectorstore.mariadb.MariaDbStoreAutoConfiguration.class,
				org.springframework.ai.autoconfigure.vectorstore.mariadb.MariaDbStoreAutoConfiguration.class
		}
)
@OpenAPIDefinition
@EnableDiscoveryClient
public class EvaluationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EvaluationServiceApplication.class, args);
	}
}
