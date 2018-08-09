package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;
    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws IOException {
        InputStream apiStockRepresentation = getClass().getResourceAsStream("STOCK_API_REPRESENTATION.json");
        byte[] byteRepresentation = ByteStreams.toByteArray(apiStockRepresentation);
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleSheetRepresentation googleSheetRepresentation = objectMapper.readValue(byteRepresentation, GoogleSheetRepresentation.class);
        String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";
        Mockito.when(restTemplate.getForObject(apiUrl, GoogleSheetRepresentation.class)).thenReturn(googleSheetRepresentation);
    }

    @Test
    public void testRetrievalOfStocks() {
        List<Stock> stocks = stockService.getStocks();
        assertNotNull(stocks);
    }

    @Test
    public void testReturnOfTop20HighestGrowthStocks() {

        List<Stock> top20HighestGrowthStocks = stockService.getUpdatedStocks();
        assertNotNull(top20HighestGrowthStocks);
    }

}