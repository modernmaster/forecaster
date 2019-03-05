package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionExecutor;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockRepository;

import java.util.List;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class PricePriceServiceIntegrationTest {

    @Autowired
    private DataAcquisitionExecutor dataAcquisitionExecutor;
    @Autowired
    private HistoricalPriceRequest historicalPriceRequest;
    @Autowired
    private StockRepository stockRepository;

    private Stock stock;

    @Before
    public void setUp() {
        stock = Stock.builder().symbol("lon:sxx").build();
        stockRepository.save(stock);
    }

    @Test
    public void testShouldDoSomething() {

        List<Stock> stockList = Lists.newArrayList(stock);
        dataAcquisitionExecutor.execute(stockList, historicalPriceRequest);
        Stock stock = stockRepository.findBySymbol("lon:sxx");
        assertThat(stock.getHistoricalPrices().size(), greaterThan(0));
    }
}
