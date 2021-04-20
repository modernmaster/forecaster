package uk.co.jamesmcguigan.forecaster.stock.trends.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.jamesmcguigan.forecaster.service.trends.strategies.RelativeStrengthIndicatorStrategy;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RelativeStrengthIndicatorStrategyTest {


    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    private static final String LON_ADM_JSON = "LON_ADM.json";
    private static final String UTC = "UTC";
    private Stock stock;
    private RelativeStrengthIndicatorStrategy relativeStrengthIndicatorStrategy;
    private SimpleDateFormat simpleDateFormat;

    @Before
    public void setUp() throws IOException {
        URL file = Resources.getResource(LON_ADM_JSON);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        stock = mapper.readValue(file, Stock.class);
        relativeStrengthIndicatorStrategy = new RelativeStrengthIndicatorStrategy();
        simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
    }

    @Test
    @Ignore
    public void testFirstAvgGainAndLossIsCorrect() {
        Trend rsi = relativeStrengthIndicatorStrategy.process(stock, Maps.newHashMap());
        assertThat(rsi.getSeries().get(13).getPointValue(), equalTo(88.33369075702338d));
    }

    @Test
    @Ignore
    public void testAvgGainAndLossIsCorrect() {
        Trend rsi = relativeStrengthIndicatorStrategy.process(stock, Maps.newHashMap());
        assertThat(rsi.getSeries().get(14).getPointValue(), equalTo(88.66103352627107d));
    }

    @Test
    @Ignore
    public void testFirst13EntriesAre0() {
        Trend rsi = relativeStrengthIndicatorStrategy.process(stock, Maps.newHashMap());
        assertThat(rsi.getSeries().get(0).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(1).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(2).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(3).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(4).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(5).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(6).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(7).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(8).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(9).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(10).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(11).getPointValue(), equalTo(0d));
        assertThat(rsi.getSeries().get(12).getPointValue(), equalTo(0d));
    }

}