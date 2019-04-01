package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class PriceServiceIntegrationTest {

    @Autowired
    private DataAcquisitionExecutor dataAcquisitionExecutor;
    @Autowired
    private HistoricalPriceRequest historicalPriceRequest;
    @Autowired
    private StockRepository stockRepository;

    private Stock stock;
    private final static String SYMBOL = "lon:sxx";

    @Before
    public void setUp() {

        stock = Stock.builder()
                .id(SYMBOL)
                .admissionDate("")
                .companyName("")
                .symbol(SYMBOL)
                .icbIndustry("")
                .icbSuperSector("")
                .countryOfIncorporation("")
                .worldRegion("")
                .market("")
                .internationalIssuer("")
                .companyMarketCap("")
                .price("")
                .percentageChange("")
                .volume("")
                .avgVolume("")
                .pe("")
                .high52("")
                .low52("")
                .delay("")
                .trends(Maps.newHashMap())
                .historicalPrices(Lists.newArrayList())
                .dateTimeUpdated(new Date())
                .dateTimeCreated(new Date())
                .build();
        stockRepository.save(stock);
    }

    @Test
    public void testShouldDoSomething() {

        List<Stock> stockList = Lists.newArrayList(stock);
        dataAcquisitionExecutor.execute(stockList, historicalPriceRequest);
        Stock stock = stockRepository.findBySymbol(SYMBOL);
        assertThat(stock.getHistoricalPrices().size(), greaterThan(0));
    }
}
