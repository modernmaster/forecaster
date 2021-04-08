package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.request.RequestService;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.request.Response;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoricalPriceServiceTests {
    @Mock
    private HistoricalPriceRepresentationTransformer historicalPriceRepresentationTransformer;
    @Mock
    private RequestService requestService;
    @Mock
    private DiscoveryClient discoveryClient;
    @Mock
    private JobService jobService;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private HistoricalPriceService historicalPriceService;

    @Before
    public void setUp() {
        historicalPriceService = new HistoricalPriceService(requestService, historicalPriceRepresentationTransformer, jobService, discoveryClient);
    }

    @Test
    @Ignore
    public void testRequestServicePriceTransformerAndAPIIsMade() throws IOException {
        String symbol = "lon:sxx";
        List<PriceRepresentation> prices = Lists.newArrayList();
        HistoricalPriceRepresentation historicalPriceRepresentation = new HistoricalPriceRepresentation(prices);
        String responseContent = Resources.toString(Resources.getResource("rawresponseforSXX.json"), Charset.defaultCharset());
        Response response = new Response(symbol, responseContent);
        when(requestService.makeRequest(symbol)).thenReturn(response);
        when(historicalPriceRepresentationTransformer.transformFrom(response)).thenReturn(historicalPriceRepresentation);
        //TODO: change to job
        historicalPriceService.processRequest(null);
        verify(requestService).makeRequest(symbol);
        verify(historicalPriceRepresentationTransformer).transformFrom(response);
        //TODO: needs to call out to stock service to persist
    }
}
