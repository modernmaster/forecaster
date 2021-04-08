package uk.co.jamesmcguigan.forecaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockServiceApplication {

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.uk.co.jamesmcguigan.forecaster.ui.forecaster");

    public static void main(String[] args) {
//        final String[] expectedVars = {"PORT", "GUESTBOOK_DB_ADDR", "UI_API_ADDR"};
//        for (String v : expectedVars) {
//            String value = System.getenv(v);
//            if (value == null) {
//                logger.error("error: {} environment variable not set", v);
//                System.exit(1);
//            }
//        }
        SpringApplication.run(StockServiceApplication.class, args);
    }

}
