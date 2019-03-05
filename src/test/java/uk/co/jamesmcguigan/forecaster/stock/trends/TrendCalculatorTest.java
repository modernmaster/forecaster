package uk.co.jamesmcguigan.forecaster.stock.trends;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrendCalculatorTest {

    @Autowired
    private TrendCalculator trendCalculator;
    private Document document;
    private static final String BEAR_PATH_TXT = "./strategies/BEAR_UPM.html";
    private static final String BULL_PATH_TXT = "./strategies/BULL_FEVR.html";

    @Test
    public void testShouldShowAStockAsBullish() throws IOException {
        Document document = loadResource(BULL_PATH_TXT);
        boolean isBearish = trendCalculator.isBearish(document);
        boolean isBullish = trendCalculator.isBullish(document);
        assertTrue(isBullish);
        assertFalse(isBearish);
    }

    @Test
    public void testShouldShowAStockAsBearish() throws IOException {
        Document document = loadResource(BEAR_PATH_TXT);
        boolean isBearish = trendCalculator.isBearish(document);
        boolean isBullish = trendCalculator.isBullish(document);
        assertTrue(isBearish);
        assertFalse(isBullish);
    }

    private Document loadResource(String file) throws IOException {
            URL url = Resources.getResource(getClass(), file);
            String html = Resources.toString(url, Charsets.UTF_8);
            return Jsoup.parse(html);
    }
}