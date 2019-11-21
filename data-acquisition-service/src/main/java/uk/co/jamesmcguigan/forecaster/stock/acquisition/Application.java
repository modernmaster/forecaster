package uk.co.jamesmcguigan.forecaster.stock.acquisition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.forecaster");

    public static void main(String[] args) {
        final String[] expectedVars = {"spring.rabbitmq.host", "spring.rabbitmq.port", "PORT", "JOB_DB_ADDR", "STOCK_API_ADDR"};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                Application.logger.error("error: {} environment variable not set", v);
                System.exit(1);
            }
        }
        SpringApplication.run(Application.class, args);
    }

}
