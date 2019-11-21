package uk.co.jamesmcguigan.forecaster.stock.acquisition.trends.strategies;

import org.springframework.stereotype.Component;

@Component
public class MovingAverageStrategy { //implements TrendStrategy {

    public static final String MOVING_AVERAGE_DURATION = "MOVING_AVERAGE_DURATION";

//    @Override
//    public Trend process(Stock stock, Map<String, String> params) {
//        return null;
//        int maDuration = Integer.parseInt(params.get(MOVING_AVERAGE_DURATION));
//        List<TrendPoint> trendPoints = Lists.newArrayList();
//        int currentStartingPoint = stock.getHistoricalPrices().size();
//        while ((currentStartingPoint - maDuration) >= 0) {
//            trendPoints.add(slidingWindow(stock, currentStartingPoint, maDuration));
//            currentStartingPoint--;
//        }
//        Collections.reverse(trendPoints);
//        return new Trend(trendPoints, new Date());
//}

//    private TrendPoint slidingWindow(Stock stock, int currentStartingPoint, int maDuration) {
//        double average = stock.getHistoricalPrices()
//                .stream()
//                .mapToDouble(Price::getClosing)
//                .skip(currentStartingPoint - maDuration)
//                .limit(maDuration)
//                .average()
//                .orElse(Double.NaN);
//        Date dateForMovingAverageValue = stock.getHistoricalPrices()
//                .get(currentStartingPoint - 1).getDate();
//        return new TrendPoint(dateForMovingAverageValue, average);
//    }
}
