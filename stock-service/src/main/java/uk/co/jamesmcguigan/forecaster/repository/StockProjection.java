package uk.co.jamesmcguigan.forecaster.repository;

import org.springframework.data.rest.core.config.Projection;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

@Projection(
        name = "stockProjection",
        types = {Stock.class})

public interface StockProjection {
    String getId();
    String getSymbol();
}
