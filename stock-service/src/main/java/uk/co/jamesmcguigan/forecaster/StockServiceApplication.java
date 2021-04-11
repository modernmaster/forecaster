package uk.co.jamesmcguigan.forecaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockServiceApplication {

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.uk.co.jamesmcguigan.forecaster.ui.forecaster");
    private static final String HOST_NAME = "HOST_NAME";
    private static final String ERROR_ENVIRONMENT_VARIABLE_NOT_SET = "error: {} environment variable not set";

    public static void main(String[] args) {
        final String[] expectedVars = {HOST_NAME};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                logger.error(ERROR_ENVIRONMENT_VARIABLE_NOT_SET, v);
                System.exit(1);
            }
        }
        SpringApplication.run(StockServiceApplication.class, args);
    }
}
