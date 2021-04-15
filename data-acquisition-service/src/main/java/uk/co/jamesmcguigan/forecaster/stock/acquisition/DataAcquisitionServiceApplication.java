package uk.co.jamesmcguigan.forecaster.service.stock.acquisition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataAcquisitionServiceApplication {

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.uk.co.jamesmcguigan.forecaster.ui.forecaster");

    public static void main(String[] args) {
        final String[] expectedVars = {"spring.rabbitmq.host", "spring.rabbitmq.port","HOST_NAME"};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                DataAcquisitionServiceApplication.logger.error("error: {} environment variable not set", v);
                System.exit(1);
            }
        }
        SpringApplication.run(DataAcquisitionServiceApplication.class, args);
    }

}
