package uk.co.jamesmcguigan.forecaster.stock.trends.point;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class PointServiceTests {
    private static final String PATH_TXT = "Path.txt";
    private PointService pointService;
    private String html;

    @Before
    public void setUp() {

        pointService = new PointService();
        try {
            URL url = Resources.getResource(getClass(),PATH_TXT);
            html = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testShouldReturnAListOfPointsForGivenSVGPath() throws Exception {

        List<Point> points = pointService.process(html);
        assertNotNull(points);
        assertThat(points.size(), equalTo(247));
        Point firstOne = points.get(0);
        assertNotNull(firstOne);
        assertThat(firstOne.getX(), equalTo(0d));
        assertThat(firstOne.getY(), equalTo(45.123887617594676d));
        Point secondOne = points.get(1);
        assertNotNull(secondOne);
        assertThat(secondOne.getX(), equalTo(2.3252032520303243d));
        assertThat(secondOne.getY(), equalTo(56.989829646580176d));

    }
}
