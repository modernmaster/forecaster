package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoricalPriceServiceTests {

    private ObjectMapper objectMapper;
    private HistoricalPriceService historicalPriceService;
    @Mock
    private StockRepository stockRepository;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private String priceJson;
    private String stockJson;

    @Before
    public void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        historicalPriceService = new HistoricalPriceService(stockRepository);
        priceJson = Resources.toString(Resources.getResource("price.json"), Charset.defaultCharset());
        stockJson = Resources.toString(Resources.getResource("stock.json"), Charset.defaultCharset());
    }

//    @Test
//    @Ignore
//    public void testUpdateWillGetStockUpdateAndThenPersist() throws IOException {
//        String symbol = "lon:sxx";
//        Price price = objectMapper.readValue(priceJson, Price.class);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        when(stockRepository.findBySymbol(symbol)).thenReturn(stock);
//        when(stockRepository.save(stock)).thenReturn(stock);
//        historicalPriceService.updatePrice(symbol, Lists.newArrayList(price));
//        verify(stockRepository).save(stock);
//        verify(stockRepository).findBySymbol(symbol);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    @Ignore
//    public void testUpdateWillGetNullStockUpdateAndThenThrowException() throws IOException {
//        String symbol = "lon:sxx";
//        Price price = objectMapper.readValue(priceJson, Price.class);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        when(stockRepository.findBySymbol(symbol)).thenReturn(null);
//        historicalPriceService.updatePrice(symbol, Lists.newArrayList(price));
//        verify(stockRepository).findBySymbol(symbol);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdateWillThrowExceptionIfSymbolIsEmpty() throws IOException {
//        Price price = objectMapper.readValue(priceJson, Price.class);
//        historicalPriceService.updatePrice(Strings.EMPTY, Lists.newArrayList(price));
//    }
//
//    @Test
//    @Ignore
//    public void testCreateHistoricalPriceEventWillCreateNewEvent() {
//        String symbol = "lon:sxx";
//        historicalPriceService.createHistoricalPriceEvent(symbol);
//        //call out to data acquisition service
//        //verify(createHistoricalPriceEvent).send();
//    }
//
//    WebTestClient
//            .bindToServer()
//            .baseUrl("http://localhost:8080")
//    .build()
//    .post()
//    .uri("/resource")
//  .exchange()
//    .expectStatus().isCreated()
//    .expectHeader().valueEquals("Content-Type", "application/json")
//    .expectBody().isEmpty();

}
