package uk.co.jamesmcguigan.forecaster.trends.strategies;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jamesmcguigan.forecaster.point.Point;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceStrategyTests {

    @Autowired
    private PriceStrategy trendStrategy;
    private Document document;
    private static final String PATH_TXT = "BEAR_UPM.html";

    @Before
    public void setUp() {
        try {
            URL url = Resources.getResource(getClass(), PATH_TXT);
            String html = Resources.toString(url, Charsets.UTF_8);
            document = Jsoup.parse(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldReturnAPopulatedSetOfPointsFromHtmlDocument() {
        List<Point> points = trendStrategy.process(document);
        assertNotNull(points);
    }
}
