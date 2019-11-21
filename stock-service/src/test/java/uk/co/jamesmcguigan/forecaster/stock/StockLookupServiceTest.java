package uk.co.jamesmcguigan.forecaster.stock;

import org.junit.Before;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;

public class StockLookupServiceTest {

    private StockLookupService stockLookupService;
    @Mock
    private StockTransformer stockTransformer;
    @Mock
    private WebClient webClient;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Before
    public void setUp() {
        stockLookupService = new StockLookupService(stockTransformer, webClient, stockRepository);
//        stockLookupService = new StockLookupService(stockTransformer, webClient, stockRepository, simpMessagingTemplate);
    }
}
