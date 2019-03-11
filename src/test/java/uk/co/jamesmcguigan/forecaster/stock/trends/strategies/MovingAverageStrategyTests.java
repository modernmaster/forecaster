package uk.co.jamesmcguigan.forecaster.stock.trends.strategies;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trends.TrendPoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertNotNull;

public class MovingAverageStrategyTests {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    private static final String FORECASTER_1_JSON = "forecaster-1.json";
    private static final String UTC = "UTC";
    private Stock stock;
    private MovingAverageStrategy movingAverageStrategy;
    private static final Map<String, String> MA_9_PARAMS = Maps.newHashMap(MovingAverageStrategy.MOVING_AVERAGE_DURATION, "9");
    private static final Map<String, String> MA_20_PARAMS = Maps.newHashMap(MovingAverageStrategy.MOVING_AVERAGE_DURATION, "20");
    private static final Map<String, String> MA_50_PARAMS = Maps.newHashMap(MovingAverageStrategy.MOVING_AVERAGE_DURATION, "50");
    private static final Map<String, String> MA_200_PARAMS = Maps.newHashMap(MovingAverageStrategy.MOVING_AVERAGE_DURATION, "200");
    private SimpleDateFormat simpleDateFormat;

    @Before
    public void setUp() throws IOException {
        URL file = MovingAverageStrategyTests.class.getResource(FORECASTER_1_JSON);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        stock = mapper.readValue(file, Stock.class);
        movingAverageStrategy = new MovingAverageStrategy();
        simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
    }

    @Test
    public void testCalculatesTheMA9ForSuppliedStockCorrectlyForLastDate() throws ParseException {

        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_9_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 8;
        Date lastExpectedDate = simpleDateFormat.parse("2019-02-25 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getPointValue(), equalTo(1953.3333333333333d));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getDate().getTime(), equalTo(lastExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA9ForSuppliedStockCorrectlyForFirstDate() throws ParseException {
        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_9_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 8;
        Date firstExpectedDate = simpleDateFormat.parse("2018-03-09 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(0).getPointValue(), equalTo(1870.5555555555557d));
        assertThat(trend.get(0).getDate().getTime(), equalTo(firstExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA20ForSuppliedStockCorrectlyForLastDate() throws ParseException {

        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_20_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 19;
        Date lastExpectedDate = simpleDateFormat.parse("2019-02-25 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getPointValue(), equalTo(1971.0d));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getDate().getTime(), equalTo(lastExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA20ForSuppliedStockCorrectlyForFirstDate() throws ParseException {
        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_20_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 19;
        Date firstExpectedDate = simpleDateFormat.parse("2018-03-26 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(0).getPointValue(), equalTo(1835.2981d));
        assertThat(trend.get(0).getDate().getTime(), equalTo(firstExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA50ForSuppliedStockCorrectlyForLastDate() throws ParseException {

        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_50_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 49;
        Date lastExpectedDate = simpleDateFormat.parse("2019-02-25 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getPointValue(), equalTo(1930.8d));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getDate().getTime(), equalTo(lastExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA50ForSuppliedStockCorrectlyForFirstDate() throws ParseException {
        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_50_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 49;
        Date firstExpectedDate = simpleDateFormat.parse("2018-05-10 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(0).getPointValue(), equalTo(1744.6880600000002d));
        assertThat(trend.get(0).getDate().getTime(), equalTo(firstExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA200ForSuppliedStockCorrectlyForLastDate() throws ParseException {

        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_200_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 199;
        Date lastExpectedDate = simpleDateFormat.parse("2019-02-25 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getPointValue(), equalTo(1929.01327d));
        assertThat(trend.get(expectedSizeForTrendCollection - 1).getDate().getTime(), equalTo(lastExpectedDate.getTime()));
    }

    @Test
    public void testCalculatesTheMA200ForSuppliedStockCorrectlyForFirstDate() throws ParseException {
        List<TrendPoint> trend = movingAverageStrategy.process(stock, MA_200_PARAMS);
        int expectedSizeForTrendCollection = stock.getHistoricalPrices().size() - 199;
        Date firstExpectedDate = simpleDateFormat.parse("2018-12-10 00:00:00.000");
        assertNotNull(trend);
        assertThat(trend.size(), greaterThan(0));
        assertThat(trend.size(), equalTo(expectedSizeForTrendCollection));
        assertThat(trend.get(0).getPointValue(), equalTo(1882.7477849999998d));
        assertThat(trend.get(0).getDate().getTime(), equalTo(firstExpectedDate.getTime()));
    }
}
