package uk.co.jamesmcguigan.forecaster.chart;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquitiesChartService implements EquitiesChart {

    private ChartRepository chartRepository;
    private ChartScraperExecutor chartScraperExecutor;

    @Override
    public Chart get(String symbol) {
        return chartRepository.findBySymbol(symbol);
    }

    @Override
    public void update() {
        List<Chart> charts = chartRepository.findAll();
        chartScraperExecutor.execute(charts);
    }

    @Override
    public void update(Chart chart) {
        chartScraperExecutor.execute(Lists.newArrayList(chart));
    }

    @Override
    public void put(String symbol) {
        chartRepository.insert(new Chart(symbol, "", ""));
    }


    public void updateSourceUri() {

    }
}
