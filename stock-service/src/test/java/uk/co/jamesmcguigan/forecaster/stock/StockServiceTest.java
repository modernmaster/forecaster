package uk.co.jamesmcguigan.forecaster.stock;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import uk.co.jamesmcguigan.forecaster.stock.liveprice.GoogleSheetRepresentation;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockLookupService stockLookupService;
    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws IOException {
        URL fileUrl = Resources.getResource("STOCK_API_REPRESENTATION.json");
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleSheetRepresentation googleSheetRepresentation = objectMapper.readValue(fileUrl, GoogleSheetRepresentation.class);
        String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";
        Mockito.when(restTemplate.getForObject(apiUrl, GoogleSheetRepresentation.class)).thenReturn(googleSheetRepresentation);
    }

    @Test
    @Ignore
    public void testRetrievalOfStocks() {
        List<Stock> stocks = stockLookupService.getStocks();
        assertNotNull(stocks);
    }

    @Test
    @Ignore
    public void testReturnOfTop20HighestGrowthStocks() {

        List<Stock> top20HighestGrowthStocks = stockLookupService.getUpdatedStocks();
        assertNotNull(top20HighestGrowthStocks);
    }

}