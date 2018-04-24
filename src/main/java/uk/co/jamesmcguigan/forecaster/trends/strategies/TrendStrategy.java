package uk.co.jamesmcguigan.forecaster.trends.strategies;

import org.jsoup.nodes.Document;
import uk.co.jamesmcguigan.forecaster.point.Point;

import java.util.List;

public interface TrendStrategy {
    List<Point> process(Document html);
}
