package uk.co.jamesmcguigan.forecaster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class IntraDayServiceApplication {

    private static final String HOST_NAME = "HOST_NAME";
    private static final String ERROR_ENVIRONMENT_VARIABLE_NOT_SET = "error: {} environment variable not set";

    public static void main(String[] args) {
        final String[] expectedVars = {HOST_NAME};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                log.error(ERROR_ENVIRONMENT_VARIABLE_NOT_SET, v);
                System.exit(1);
            }
        }
        SpringApplication.run(IntraDayServiceApplication.class, args);
    }
}
