package uk.co.jamesmcguigan.forecaster.service.pattern.strategies;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.jamesmcguigan.forecaster.repository.PatternRepository;
import uk.co.jamesmcguigan.forecaster.service.pattern.PatternTransaction;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.price.HistoricalPrice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BullishStrategyTest {

    private BullishStrategy bullishStrategy;
    @Mock
    private PatternRepository patternRepository;


    @Before
    public void setUp() {

        bullishStrategy = new BullishStrategy(patternRepository);
    }

    @Test
    public void testLinearGrowthIsBullishShouldReturnTrue() {
        when(patternRepository.save(ArgumentMatchers.any())).thenReturn(PatternTransaction.builder().id(1L).build());
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "5");
        List<HistoricalPrice> historicalPriceList = Lists.newArrayList();
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(2d));
        historicalPriceList.add(buildTestHistoricalPrice(3d));
        historicalPriceList.add(buildTestHistoricalPrice(4d));
        historicalPriceList.add(buildTestHistoricalPrice(5d));
        historicalPriceList.add(buildTestHistoricalPrice(6d));
        Stock stock = buildTestStock(10d, historicalPriceList);
        bullishStrategy.process(stock, params);
        verify(patternRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void testGrowthFlucuationIsBullishShouldReturnTrue() {
        when(patternRepository.save(ArgumentMatchers.any())).thenReturn(PatternTransaction.builder().id(1L).build());
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "5");
        List<HistoricalPrice> historicalPriceList = Lists.newArrayList();
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(2d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(4d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(3d));
        Stock stock = buildTestStock(10d, historicalPriceList);
        bullishStrategy.process(stock, params);
        verify(patternRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void testLinearGrowthButCurrentPriceFailIsNotBullishShouldReturnFalse() {
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "5");
        List<HistoricalPrice> historicalPriceList = Lists.newArrayList();
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(2d));
        historicalPriceList.add(buildTestHistoricalPrice(3d));
        historicalPriceList.add(buildTestHistoricalPrice(4d));
        historicalPriceList.add(buildTestHistoricalPrice(5d));
        historicalPriceList.add(buildTestHistoricalPrice(6d));
        Stock stock = buildTestStock(0.9d, historicalPriceList);
        bullishStrategy.process(stock, params);
        verify(patternRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    public void testGrowthFlucuationIsBullishOverFourDaysShouldReturnTrue() {
        when(patternRepository.save(ArgumentMatchers.any())).thenReturn(PatternTransaction.builder().id(1L).build());
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "4");
        List<HistoricalPrice> historicalPriceList = Lists.newArrayList();
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(2d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(4d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(3d));
        Stock stock = buildTestStock(10d, historicalPriceList);
        bullishStrategy.process(stock, params);
        verify(patternRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void testGrowthFlucuationIsBullishOverFourDaysShouldReturnFalse() {
        Map<String, String> params = Maps.newHashMap();
        params.put("WINDOW", "4");
        List<HistoricalPrice> historicalPriceList = Lists.newArrayList();
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(2d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(4d));
        historicalPriceList.add(buildTestHistoricalPrice(1d));
        historicalPriceList.add(buildTestHistoricalPrice(3d));
        Stock stock = buildTestStock(1d, historicalPriceList);
        bullishStrategy.process(stock, params);
        verify(patternRepository, never()).save(ArgumentMatchers.any());
    }

    private Stock buildTestStock(Double price, List<HistoricalPrice> historicalPriceList) {
        return Stock.builder()
                .id("id")
                .admissionDate("date")
                .companyName("company")
                .symbol("symbol")
                .icbIndustry("icbindustry")
                .icbSuperSector("icbsupersector")
                .countryOfIncorporation("counrty")
                .worldRegion("worldregion")
                .market("market")
                .internationalIssuer("internationalIssuer")
                .companyMarketCap("companyMarketCap")
                .price(price)
                .percentageChange(0d)
                .avgVolume(0)
                .volume(0)
                .pe(0d)
                .high52(0d)
                .low52(0d)
                .delay("0")
                .historicalPrices(historicalPriceList)
                .trends(Maps.newHashMap())
                .patterns(Lists.newArrayList())
                .build();
    }

    private HistoricalPrice buildTestHistoricalPrice(Double price) {
        Date date = new Date();
        return HistoricalPrice.builder().opening(0d).closing(price).daysHigh(0d).daysLow(0d).date(date).dateTimeCreated(date).build();
    }
}