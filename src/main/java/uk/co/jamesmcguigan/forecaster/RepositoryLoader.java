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
        }
    }

