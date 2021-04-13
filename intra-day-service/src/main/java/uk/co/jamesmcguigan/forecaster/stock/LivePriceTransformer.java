package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePrice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class LivePriceTransformer {

    public List<LivePrice> transform(List<Stock> stock) {
        return stock.stream()
                .map(this::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public LivePrice transform(Stock stock) {
        return LivePrice.builder()
                .symbol(stock.getSymbol())
                .price(stock.getPrice())
                .volume(stock.getVolume())
                .build();
    }
}
