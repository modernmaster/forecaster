package uk.co.jamesmcguigan.forecaster.trends;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.point.Point;
import uk.co.jamesmcguigan.forecaster.trends.Strategies.TrendStrategy;

import java.util.List;

@Service
public class TrendEngine {
    private TrendStrategy trendStrategy;

    public List<Point> process(Document document) {
        return trendStrategy.process(document);
    }

    public void setTrendStrategy(TrendStrategy trendStrategy) {
        this.trendStrategy = trendStrategy;
    }

}
