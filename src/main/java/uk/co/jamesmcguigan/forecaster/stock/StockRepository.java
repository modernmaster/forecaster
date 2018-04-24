package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends PagingAndSortingRepository<Stock, String> {
}
