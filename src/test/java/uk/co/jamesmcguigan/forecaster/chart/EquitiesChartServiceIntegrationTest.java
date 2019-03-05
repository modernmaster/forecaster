package uk.co.jamesmcguigan.forecaster.chart;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class EquitiesChartServiceIntegrationTest {

    @Autowired
    private ChartScraperExecutor chartScraperExecutor;

    @Autowired
    private ChartRepository chartRepository;

    @Test
    public void testShouldDoSomething() {
        String uri = "https://www.londonstockexchange.com/exchange/prices-and-markets/stocks/summary/company-summary-chart.html?fourWayKey=GB00B0DG3H29GBGBXSTMM";
        Chart chart = new Chart("lon:sxx", uri, "<html>");

        chartScraperExecutor.execute(Lists.newArrayList(chart));

        chartRepository.save(chart);
//        equitiesChart.update();
        assertThat(1, equalTo(chartRepository.findAll().size()));

    }
}
