package unicauca.edu.co.sga.evaluation_service;

import org.springframework.ai.autoconfigure.vectorstore.mariadb.MariaDbStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				// Maybe this could create a conflict with the data of the database
				// because it's store of mariaDB, I think that.s
				MariaDbStoreAutoConfiguration.class // With this you can exclude the error.
		}
)
public class EvaluationServiceApplication {
	public static void main(String[] args) { SpringApplication.run(EvaluationServiceApplication.class, args); }
}
