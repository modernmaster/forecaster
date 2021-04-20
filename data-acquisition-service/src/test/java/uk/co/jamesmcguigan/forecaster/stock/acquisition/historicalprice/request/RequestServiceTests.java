package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.request;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class RequestServiceTests {

    private RequestService requestService;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private static final String PRICE_SOURCE_URL = "http://charts.londonstockexchange.com/WebCharts/services/ChartWService.asmx/GetPrices";

    @Before
    public void setUp() {
        requestService = new RequestService();
    }

    @Test
    @Ignore
    public void test() throws IOException {
        String symbol = "lon:sxx";
        String responseContent = Resources.toString(Resources.getResource("rawresponseforSXX.json"), Charset.defaultCharset());
//        when(httpRequestClient.send(eq(PRICE_SOURCE_URL), any())).thenReturn(responseContent);
        Response response = requestService.makeRequest(symbol);
        assertThat(response.getSymbol(), equalTo(symbol));
        assertThat(response.getContents(), equalTo(responseContent));
        //verify(httpRequestClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIfSymbolIsTooShort() throws IOException {
        String symbol = "sxx";
        requestService.makeRequest(symbol);
    }

}
