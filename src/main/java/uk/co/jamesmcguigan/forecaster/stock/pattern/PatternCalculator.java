package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.notification.PushNotificationService;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

@Service
@RequiredArgsConstructor
public class PatternCalculator {

    public boolean isBullish(Stock stock) {
        return false;
    }

    public boolean isBearish(Stock stock) {
        return false;
    }

    public boolean isApproachGoldenCross(Stock stock) {
        //MA50 on a downward trend crosses MA20... share is usually lower than both
        return false;
    }

    public boolean isApproachingMA20Support(Stock stock) {
        return false;
    }

    public boolean hasBrokenMA20Resistance(Stock stock) {
        return false;
    }
}

//  public boolean isBullish(Document html) {
//
//    List<Point> fifty = getTrend(html, maFiftyStrategy);
//    List<Point> twenty = getTrend(html, maTwentyStrategy);
//    boolean isBullish = true;
//    int max = 20;
//    List<Point> subSetFifty = fifty.subList(fifty.size() - max, fifty.size());
//    List<Point> subSetTwenty = twenty.subList(twenty.size() - max, twenty.size());
//
//    for (int i = 0; i < max; i++) {
//      if (!isBullish) {
//        break;
//      }
//      isBullish = subSetFifty.get(i).compareTo(subSetTwenty.get(i)) > 0;
//    }
//    return isBullish;
//  }
//
//  public boolean isBearish(Document html) {
//
//    List<Point> fifty = getTrend(html, maFiftyStrategy);
//    List<Point> twenty = getTrend(html, maTwentyStrategy);
//    int max = 20;
//    List<Point> subSetFifty = fifty.subList(fifty.size() - max, fifty.size());
//    List<Point> subSetTwenty = twenty.subList(twenty.size() - max, twenty.size());
//
//    boolean isBearish = true;
//    for (int i = 0; i < max; i++) {
//      if (!isBearish) {
//        break;
//      }
//      isBearish = subSetFifty.get(i).compareTo(subSetTwenty.get(i)) < 0;
//    }
//    return isBearish;
//  }
//
//  public boolean isApproachGoldenCross() {
//    //MA50 on a downward trend crosses MA20... share is usually lower than both
//    return false;
//  }
//
//  public boolean isApproachingMA20Support() {
//    return false;
//  }
//
//  public boolean hasBrokenMA20Resistance() {
//    return false;
//  }
//
//  private List<Point> normaliseTrendData(List<Point> points) {
//    return null;
//  }
//
//  private List<Point> getTrend(Document html, TrendStrategy trendStrategy) {
//    trendContext.setTrendStrategy(trendStrategy);
//    return trendContext.process(html);
//  }
