package unicauca.edu.co.sga.evaluation_service;

import org.springframework.ai.autoconfigure.vectorstore.mariadb.MariaDbStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
		exclude = {
				// Maybe this could create a conflict with the data of the database
				// because it's store of mariaDB, I think that.s
				MariaDbStoreAutoConfiguration.class
		}
)
@EnableDiscoveryClient
public class EvaluationServiceApplication {
	public static void main(String[] args) { SpringApplication.run(EvaluationServiceApplication.class, args); }
}
