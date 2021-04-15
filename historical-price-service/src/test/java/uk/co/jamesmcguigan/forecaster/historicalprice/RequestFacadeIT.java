package uk.co.jamesmcguigan.forecaster.historicalprice;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.jamesmcguigan.forecaster.facade.StockServiceClient;
import uk.co.jamesmcguigan.forecaster.facade.request.HistoricalPriceRepresentationTransformer;
import uk.co.jamesmcguigan.forecaster.facade.request.RequestFacade;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RequestFacadeIT {

    private final static String LON_BOO = "lon:boo";
    private RequestFacade requestFacade;
    private HistoricalPriceRepresentationTransformer historicalPriceRepresentationTransformer;
    @Mock
    private StockServiceClient stockServiceClient;

    @Before
    public void setUp() {
        requestFacade = new RequestFacade(LON_BOO, stockServiceClient, historicalPriceRepresentationTransformer);
    }

    @Test
    public void doSomething() throws IOException {
        String symbol = "lon:boo";
        requestFacade.run();
        verify(stockServiceClient).patch("lon:boo", any());
    }
}
