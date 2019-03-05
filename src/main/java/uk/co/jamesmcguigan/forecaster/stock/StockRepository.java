package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {
    Stock findBySymbol(String symbol);
}
