package uk.co.jamesmcguigan.forecaster.stock.trends;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;
import uk.co.jamesmcguigan.forecaster.stock.trend.TrendsService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrendsServiceTests {
    private ObjectMapper objectMapper;
    private TrendsService trendsService;
    @Mock
    private StockRepository stockRepository;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private String trendsJson;
    private String stockJson;

    @Before
    public void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        trendsService = new TrendsService(stockRepository);
        trendsJson = Resources.toString(Resources.getResource("trends.json"), Charset.defaultCharset());
        stockJson = Resources.toString(Resources.getResource("stock.json"), Charset.defaultCharset());
    }

    @Test
    public void testUpdateWillGetStockUpdateAndThenPersist() throws IOException {
        String symbol = "lon:sxx";
        Map<String, Trend> trends = objectMapper.readValue(trendsJson, Map.class);
        Stock stock = objectMapper.readValue(stockJson, Stock.class);
        when(stockRepository.findBySymbol(symbol)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(stock);
        trendsService.updateTrend(symbol, Maps.newHashMap(trends));
        verify(stockRepository).save(stock);
        verify(stockRepository).findBySymbol(symbol);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWillGetNullStockUpdateAndThenThrowException() throws IOException {
        String symbol = "lon:sxx";
        Map<String, Trend> trends = objectMapper.readValue(trendsJson, Map.class);
        Stock stock = objectMapper.readValue(stockJson, Stock.class);
        when(stockRepository.findBySymbol(symbol)).thenReturn(null);
        trendsService.updateTrend(symbol, Maps.newHashMap(trends));
        verify(stockRepository).findBySymbol(symbol);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWillThrowExceptionIfSymbolIsEmpty() throws IOException {
        Map<String, Trend> trends = objectMapper.readValue(trendsJson, Map.class);
        trendsService.updateTrend(Strings.EMPTY, Maps.newHashMap(trends));
    }

    @Test
    public void testCreateHistoricalPriceEventWillCreateNewEvent() {
        String symbol = "lon:sxx";
        trendsService.createTrendEvent(symbol);
        //TODO verify call to rest service
//        verify(createTrendEvent).send();
    }

}
