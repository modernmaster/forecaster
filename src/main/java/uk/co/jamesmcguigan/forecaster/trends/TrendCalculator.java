package uk.co.jamesmcguigan.forecaster.trends;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.point.Point;
import uk.co.jamesmcguigan.forecaster.trends.Strategies.MAFiftyStrategy;
import uk.co.jamesmcguigan.forecaster.trends.Strategies.MATwentyStrategy;
import uk.co.jamesmcguigan.forecaster.trends.Strategies.PriceStrategy;
import uk.co.jamesmcguigan.forecaster.trends.Strategies.TrendStrategy;

import java.util.List;

@Service
public class TrendCalculator {

    @Autowired
    private TrendEngine trendEngine;
    @Autowired
    private MAFiftyStrategy maFiftyStrategy;
    @Autowired
    private MATwentyStrategy maTwentyStrategy;
    @Autowired
    private PriceStrategy priceStrategy;

    public boolean isBullish(Document html) {

        List<Point> fifty = getTrend(html, maFiftyStrategy);
        List<Point> twenty = getTrend(html, maTwentyStrategy);
        boolean isBullish = true;
        int max = 20;
        List<Point> subSetFifty = fifty.subList(fifty.size()-max, fifty.size());
        List<Point> subSetTwenty = twenty.subList(twenty.size()-max, twenty.size());

        for(int i=0;i<max;i++) {
            if(!isBullish) {
                break;
            }
            isBullish = subSetFifty.get(i).compareTo(subSetTwenty.get(i)) > 0;
        }
        return isBullish;
    }

    public boolean isBearish(Document html) {

        List<Point> fifty = getTrend(html, maFiftyStrategy);
        List<Point> twenty = getTrend(html, maTwentyStrategy);
        int max = 20;
        List<Point> subSetFifty = fifty.subList(fifty.size()-max, fifty.size());
        List<Point> subSetTwenty = twenty.subList(twenty.size()-max, twenty.size());

        boolean isBearish = true;
        for(int i=0;i<max;i++) {
            if(!isBearish) {
                break;
            }
            isBearish = subSetFifty.get(i).compareTo(subSetTwenty.get(i)) < 0;
        }
        return isBearish;
    }

    public boolean isApproachGoldenCross() {
        //MA50 on a downward trend crosses MA20... share is usually lower than both
        return false;
    }

    public boolean isApproachingMA20Support() {
        return false;
    }

    public boolean hasBrokenMA20Resistance() {
        return false;
    }

    private List<Point> normaliseTrendData(List<Point> points) {
        return  null;
    }

    private List<Point> getTrend(Document html, TrendStrategy trendStrategy) {
        trendEngine.setTrendStrategy(trendStrategy);
        return trendEngine.process(html);
    }




}
