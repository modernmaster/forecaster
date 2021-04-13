package uk.co.jamesmcguigan.forecaster.stock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.repository.LivePriceRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockRetrievalServiceIT {

    private StockRetrievalService stockLookupService;
    @Autowired
    private LivePriceTransformer livePriceTransformer;
    @Autowired
    private StockTransformer stockTransformer;
    @Mock
    private LivePriceRepository livePriceRepository;
    @Mock
    private StockApiClient stockApiClient;
    @Mock
    private TrendsMessageProducer trendsMessageProducer;
    @Autowired
    private WebClient webClient;

    @Before
    public void setUp() {
        stockLookupService = new StockRetrievalService(trendsMessageProducer, stockTransformer, livePriceTransformer, livePriceRepository, webClient, stockApiClient);
        stockLookupService.updateStocks();
    }

    @Test
    public void testThatUpdatedStocksWillBeReturnedAndSentToStockAndTrendService() {
        when(livePriceRepository.save(any())).thenReturn(null);
        stockLookupService.updateStocks();
        verify(stockApiClient, times(2462)).post(any());
        verify(livePriceRepository, times(2462)).save(any());
        verify(trendsMessageProducer, times(2462)).sendMessage(any());
    }

}
