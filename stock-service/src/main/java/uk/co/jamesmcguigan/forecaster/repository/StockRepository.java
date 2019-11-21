package uk.co.jamesmcguigan.forecaster.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {
    Stock findBySymbol(String symbol);
}
