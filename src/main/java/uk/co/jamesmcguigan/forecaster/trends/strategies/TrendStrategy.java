package uk.co.jamesmcguigan.forecaster.trends.strategies;

import uk.co.jamesmcguigan.forecaster.point.Point;

import org.jsoup.nodes.Document;

import java.util.List;

public interface TrendStrategy {

  List<Point> process(Document html);
}
