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
public class MAFiftyStrategy implements TrendStrategy {

  @Autowired
  private PointService pointService;

  public List<Point> process(Document html) {
    Elements maFiftyElement = html.select(".highcharts-series path[stroke=\"#00b000\"");
    try {
      return pointService.process(maFiftyElement.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}