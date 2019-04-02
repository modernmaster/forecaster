package uk.co.jamesmcguigan.forecaster.stock.pattern;

import java.util.Date;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternCalculatorTests {

    private PatternCalculator patternCalculator;


    @Before
    public void setUp() {
        patternCalculator = new PatternCalculator();
    }


    @Test
    public void doSomething() {
        Stock stock = Stock.builder()
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
                .price("10")
                .percentageChange("0")
                .avgVolume("0")
                .volume("0")
                .pe("0")
                .high52("0")
                .low52("0")
                .delay("0")
                .historicalPrices(Lists.newArrayList())
                .trends(Maps.newHashMap())
                .dateTimeCreated(new Date())
                .dateTimeUpdated(new Date())
                .build();
        assertThat(patternCalculator.isBullish(stock), equalTo(true));
    }
}
