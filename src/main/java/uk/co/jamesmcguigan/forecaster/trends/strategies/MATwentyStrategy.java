package uk.co.jamesmcguigan.forecaster.trends.strategies;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.point.Point;
import uk.co.jamesmcguigan.forecaster.point.PointService;

import java.util.Collections;
import java.util.List;

@Service
public class MATwentyStrategy implements TrendStrategy {

    @Autowired
    private PointService pointService;

    @Override
    public List<Point> process(Document html) {

        // twenty is red! stroke="#ff0000"
        Elements maTwentyElement = html.select(".highcharts-series path[stroke=\"#ff0000\"]");
        try {
            return pointService.process(maTwentyElement.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
