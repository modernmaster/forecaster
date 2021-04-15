package uk.co.jamesmcguigan.forecaster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HistoricalPriceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HistoricalPriceServiceApplication.class, args);
    }
}
