package uk.co.jamesmcguigan.forecaster.trends.strategies;

import uk.co.jamesmcguigan.forecaster.point.Point;
import uk.co.jamesmcguigan.forecaster.point.PointService;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PriceStrategy implements TrendStrategy {

  @Autowired
  private PointService pointService;

  //price is #142635
  public List<Point> process(Document html) {
    Elements graphPoints = html.select(".highcharts-series path[stroke=\"#142635\"]");
    try {
      return pointService.process(graphPoints.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}