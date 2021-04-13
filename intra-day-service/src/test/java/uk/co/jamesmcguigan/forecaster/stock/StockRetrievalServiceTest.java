package uk.co.jamesmcguigan.forecaster.stock;

import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.repository.LivePriceRepository;

public class StockRetrievalServiceTest {

    private StockRetrievalService stockLookupService;
    private LivePriceTransformer livePriceTransformer;
    private StockTransformer stockTransformer;
    @Mock
    private LivePriceRepository livePriceRepository;
    @Mock
    private StockApiClient stockApiClient;
    @Mock
    private TrendsMessageProducer trendsMessageProducer;
    @Mock
    private WebClient webClient;

    @Before
    public void setUp() {
        stockLookupService = new StockRetrievalService(trendsMessageProducer, stockTransformer, livePriceTransformer, livePriceRepository, webClient, stockApiClient);
    }
    //TODO: add unit tests
}
