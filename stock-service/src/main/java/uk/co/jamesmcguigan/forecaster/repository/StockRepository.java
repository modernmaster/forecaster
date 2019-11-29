package uk.co.jamesmcguigan.forecaster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

    Stock findBySymbol(@Param("symbol") String symbol);

    Page<Stock> findByCompanyNameStartsWith(@Param("companyName") String companyName,
                                            Pageable pageable);
}
