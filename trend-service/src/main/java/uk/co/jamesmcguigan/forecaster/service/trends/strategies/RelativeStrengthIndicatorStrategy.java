package uk.co.jamesmcguigan.forecaster.service.trends.strategies;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.price.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;
import uk.co.jamesmcguigan.forecaster.stock.trend.TrendPoint;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class RelativeStrengthIndicatorStrategy implements TrendStrategy {
    public static final String RSI = "RSI";
    private static final String GAINS = "GAINS";
    private static final String LOSSES = "LOSSES";
    private static final String AVG_GAIN = "AVG_GAIN";
    private static final String AVG_LOSS = "AVG_LOSS";
    private static final String RSI_DEFAULT_VALUE = "14";

    @Override
    public Trend process(Stock stock, Map<String, String> params) {
        int rsiPeriod = Integer.valueOf(params.getOrDefault(RSI, RSI_DEFAULT_VALUE));
        LinkedHashMap<Date, Map<String, Double>> calculateGainsLosses = Maps.newLinkedHashMap();
        if (stock.getHistoricalPrices().size() >= rsiPeriod) {
            calculateGainsLosses.putAll(calculateGainsLosses(stock));
            calculateFirstAverageGainsLosses(calculateGainsLosses, rsiPeriod);
            calculateAverageGainsLosses(calculateGainsLosses, rsiPeriod);
            calculateRSI(calculateGainsLosses);
        }
        return transformToTrend(calculateGainsLosses);
    }

    private Trend transformToTrend(LinkedHashMap<Date, Map<String, Double>> calculateGainsLosses) {
        List<TrendPoint> trendPoints = Lists.newArrayList();
        calculateGainsLosses.entrySet().stream().forEach(
                x -> trendPoints.add(
                        new TrendPoint(x.getKey(), x.getValue().getOrDefault(RSI, 0d)))
        );
        return new Trend(trendPoints, new Date());
    }

    private LinkedHashMap<Date, Map<String, Double>> calculateGainsLosses(Stock stock) {
        LinkedHashMap<Date, Map<String, Double>> gainsLosses = new LinkedHashMap<>();
        List<HistoricalPrice> prices = stock.getHistoricalPrices();
        for (int i = 1; i < prices.size(); i++) {
            Map<String, Double> gl = Maps.newHashMap();
            double difference = prices.get(i).getClosing() - prices.get(i - 1).getClosing();
            if (difference > 0) {
                gl.put(GAINS, difference);
                gl.put(LOSSES, 0d);
            } else {
                gl.put(GAINS, 0d);
                gl.put(LOSSES, Math.abs(difference));
            }
            gainsLosses.put(prices.get(i).getDate(), gl);
        }
        return gainsLosses;
    }

    private void calculateFirstAverageGainsLosses(LinkedHashMap<Date, Map<String, Double>> gls, int rsiPeriod) {
        AtomicReference<Double> sumGains = new AtomicReference<>((double) 0);
        AtomicReference<Double> sumLosses = new AtomicReference<>((double) 0);

        gls.entrySet().stream().limit(rsiPeriod).forEach(entry -> {
            sumGains.updateAndGet(v -> v + entry.getValue().get(GAINS));
            sumLosses.updateAndGet(v -> v + entry.getValue().get(LOSSES));
        });

        List<Map<String, Double>> values = new ArrayList<>(gls.values());
        double avgGain = sumGains.get() / rsiPeriod;
        double avgLoss = sumLosses.get() / rsiPeriod;
        values.get(rsiPeriod - 1).put(AVG_GAIN, avgGain);
        values.get(rsiPeriod - 1).put(AVG_LOSS, avgLoss);
    }

    private void calculateAverageGainsLosses(LinkedHashMap<Date, Map<String, Double>> gls, int rsiPeriod) {
        List<Map<String, Double>> values = new ArrayList<>(gls.values());
        for (int i = rsiPeriod; i < values.size(); i++) {
            double avgGain = ((values.get(i - 1).get(AVG_GAIN) * 13) + values.get(i).get(GAINS)) / 14;
            double avgLoss = ((values.get(i - 1).get(AVG_LOSS) * 13) + values.get(i).get(LOSSES)) / 14;
            values.get(i).put(AVG_GAIN, avgGain);
            values.get(i).put(AVG_LOSS, avgLoss);
        }
    }

    private void calculateRSI(LinkedHashMap<Date, Map<String, Double>> gls) {
        int rsiPeriod = 14;
        List<Map<String, Double>> values = new ArrayList<>(gls.values());
        for (int i = rsiPeriod - 1; i < values.size(); i++) {
            double rs = values.get(i).get(AVG_GAIN) / values.get(i).get(AVG_LOSS);
            double rsi = 100 - (100 / (1 + rs));
            values.get(i).put(RSI, rsi);
        }
    }
}