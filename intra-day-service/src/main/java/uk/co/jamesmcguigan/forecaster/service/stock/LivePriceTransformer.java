package uk.co.jamesmcguigan.forecaster.service.stock;

import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class LivePriceTransformer {

    public List<LivePriceEvent> transform(List<Stock> stock) {
        return stock.stream()
                .map(this::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public LivePriceEvent transform(Stock stock) {
        return LivePriceEvent.builder()
                .symbol(stock.getSymbol())
                .price(stock.getPrice())
                .volume(stock.getVolume())
                .build();
    }
}
