package uk.co.jamesmcguigan.forecaster.point;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PointService {

    private static final String HTML_FORMATING_ERROR = "Html formating error";
    private static final String REGEX_POINTS_EXTRACTION = "<path fill=\".*\" d=\"([-MLC 0-9.]+)\".*></path>";
    private static final String REGEX_POINT_EXTRACTION = "[MLC]";

    public List<Point> process(final String html) throws Exception {

        Matcher matcher = extractMatchingElements(html);
        if (!matcher.matches()) {
            throw new Exception(HTML_FORMATING_ERROR);
        }
        String firstElement = matcher.group(1);
        return new ArrayList<>(getFollowingPoints(firstElement));
    }

    private Matcher extractMatchingElements(String html) {
        Pattern p = Pattern.compile(REGEX_POINTS_EXTRACTION);
        return p.matcher(html);
    }

    private List<Point> getFollowingPoints(String remainingElements) {
        List<Point> points = Lists.newArrayList();
        String[] elementPoints = remainingElements.split(REGEX_POINT_EXTRACTION);
        for (String elementPoint : elementPoints) {
            if (!Strings.isEmpty(elementPoint)) {
                String[] delimitedPoints = elementPoint.trim().split(" ");
                Point point = new Point(Double.parseDouble(delimitedPoints[0]), Double.parseDouble(delimitedPoints[1]));
                points.add(point);
            }
        }
        return points;
    }

}
