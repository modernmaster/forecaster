package uk.co.jamesmcguigan.forecaster.service.pattern.strategies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.repository.PatternRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.pattern.IntervalWindow;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternType;
import uk.co.jamesmcguigan.forecaster.stock.price.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.service.pattern.PatternTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BullishStrategy implements PatternStrategy {

    private final PatternRepository patternRepository;

    @Override
    public Optional<PatternTransaction> process(Stock stock, Map<String, String> params) {
        List<Double> historicalPriceList = stock.getHistoricalPrices().stream().map(HistoricalPrice::getClosing).collect(Collectors.toList());
        historicalPriceList.add(stock.getPrice());
        IntervalWindow intervalWindow = IntervalWindow.fromValue(Integer.parseInt(params.get("WINDOW")));
        Double currentPrice = historicalPriceList.get(historicalPriceList.size() - 1);
        Double firstPrice = historicalPriceList.get(historicalPriceList.size() - 1 - intervalWindow.getValue());
        if (firstPrice >= currentPrice) {
            return Optional.empty();
        }
        if (stock.getPatterns() != null) {
            return savePatternTransaction(stock, intervalWindow);
        }
        return Optional.empty();
    }

    private Optional<PatternTransaction> savePatternTransaction(Stock stock, IntervalWindow intervalWindow) {
        if (stock.getPatterns().stream()
                .findAny().stream().noneMatch(x -> x.getPatternType().equals(PatternType.BULLISH))) {
            PatternTransaction patternTransaction = PatternTransaction.builder()
                    .createdDateTime(LocalDateTime.now())
                    .identifiedPrice(stock.getPrice())
                    .intervalWindow(intervalWindow.getValue())
                    .pattern(PatternType.BULLISH)
                    .symbol(stock.getSymbol())
                    .build();
            return Optional.of(patternRepository.save(patternTransaction));
        }
        return Optional.empty();
    }
}


