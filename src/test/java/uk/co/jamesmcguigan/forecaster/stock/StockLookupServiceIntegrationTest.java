package uk.co.jamesmcguigan.forecaster.stock;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockLookupServiceIntegrationTest {

    @Autowired
    private StockLookupService stockLookupService;

    @Before
    public void setUp() {
        stockLookupService.updateStocks();
    }

    @Test
    public void testThatAnEmptyDatasourceWillReturnTop100Items() {
        List<Stock> stocks = stockLookupService.getStocks();
        assertThat(stocks.size(), equalTo(100));
    }

    @Test
    public void testThatUpdatedStocksWillBeReturned() {
        List<Stock> stocks = stockLookupService.getUpdatedStocks();
        assertThat(stocks.size(), IsNot.not(equalTo(100)));
    }

}
