package uk.co.jamesmcguigan.forecaster.stock.trends.strategies;

import uk.co.jamesmcguigan.forecaster.stock.trends.point.Point;

import org.jsoup.nodes.Document;

import java.util.List;

public interface TrendStrategy {

  List<Point> process(Document html);
}
