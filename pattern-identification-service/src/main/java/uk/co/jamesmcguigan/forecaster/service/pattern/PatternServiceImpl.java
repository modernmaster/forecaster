package uk.co.jamesmcguigan.forecaster.service.pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import uk.co.jamesmcguigan.forecaster.facade.PatternRepresentation;
import uk.co.jamesmcguigan.forecaster.facade.StockApiClient;
import uk.co.jamesmcguigan.forecaster.service.pattern.strategies.PatternStrategy;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.pattern.Pattern;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatternServiceImpl implements PatternService {

    private static final String CALLING_OUT_TO_STOCK_API_FOR = "Calling out to stock api for {}";
    private static final String NO_HISTORICAL_PRICES_FOR_STOCK = "No historical prices for stock {}";
    @Autowired
    private final StockApiClient stockApiClient;
    @Autowired
    private final PatternStrategy bullishStrategy;
    @Autowired
    private final PatternTransformer patternTransformer;

    @Override
    public void processEvent(PatternEvent patternEvent) {
        log.debug(CALLING_OUT_TO_STOCK_API_FOR, patternEvent.getSymbol());
        //Change here because of an increased size in mutuable attributes that are being passed in event!
        Stock stock = stockApiClient.get(patternEvent.getSymbol());
        assert stock != null;
        if (CollectionUtils.isEmpty(stock.getHistoricalPrices())) {
            log.debug(NO_HISTORICAL_PRICES_FOR_STOCK, stock.getSymbol());
            return;
        }
        if(stock.getPatterns()==null) {
            stock.setPatterns(Lists.newArrayList());
        }
        List<PatternTransaction> patternTransactionList = Lists.newArrayList();
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "4");
        bullishStrategy.process(stock, params).ifPresent(patternTransactionList::add);
        List<Pattern> patterns = patternTransactionList.stream().map(patternTransformer::transform).collect(Collectors.toList());
        List<Pattern> updatedPatternList = removedExpiredAndAddNewPatterns(stock, patterns);
        stockApiClient.patch(patternEvent.getSymbol(), new PatternRepresentation(updatedPatternList));
    }

    private List<Pattern> removedExpiredAndAddNewPatterns(Stock stock, List<Pattern> patterns) {
        List<Pattern> mergedPatternLists = Lists.newArrayList();
        stock.getPatterns().forEach(
                x-> patterns.forEach(
                        y -> {
                            if(y.getPatternType().equals(x.getPatternType()))
                            {
                                mergedPatternLists.add(x);
                            }
                        }
                )
        );
        mergedPatternLists.addAll(
                patterns.stream()
                    .filter(x -> mergedPatternLists.stream().noneMatch(y -> y.getPatternType().equals(x.getPatternType())))
                    .collect(Collectors.toList())
        );
        return mergedPatternLists;
    }
}
