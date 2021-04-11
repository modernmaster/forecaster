package uk.co.jamesmcguigan.forecaster.stock;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockLookupServiceIntegrationTest {

    private StockLookupService stockLookupService;
    @Autowired
    private WebClient webClient;
    @Autowired
    private StockTransformer stockTransformer;
    @Mock
    private StockRepository stockRepository;

    @Before
    public void setUp() {
        stockLookupService = new StockLookupService(stockTransformer, webClient, stockRepository);
        stockLookupService.updateStocks();
    }

    @Test
    public void testThatUpdatedStocksWillBeReturned() {
        when(stockRepository.saveAll(any())).thenReturn(Collections.emptyList());
        List<Stock> stocks = stockLookupService.getUpdatedStocks();
        assertThat(stocks.size(), IsNot.not(equalTo(100)));
        verify(stockRepository, times(3)).saveAll(any());
    }

}
