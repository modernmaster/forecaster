package uk.co.jamesmcguigan.forecaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockRepository;

@Component
public class RepositoryLoader implements CommandLineRunner{

        private final StockRepository repository;

        @Autowired
        public RepositoryLoader(StockRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... strings) throws Exception {
            this.repository.save(new Stock("02/08/2006",
                    "1PM PLC",
                    "LON:OPM",
                    "Financials",
                    "Financial Services",
                    "United Kingdom",
                    "Europe",
                    "AIM ",
                    "No",
                    "£39,063,467.00",
                    "47.3",
                    "0.64",
                    "126042",
                    "8953",
                    "8.49",
                    "62",
                    "39.75",
                    "0"));
        }
    }

