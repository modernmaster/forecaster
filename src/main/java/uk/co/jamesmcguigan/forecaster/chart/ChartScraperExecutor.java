package uk.co.jamesmcguigan.forecaster.chart;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.Application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
class ChartScraperExecutor {

    private ScrapeRequest scrapeRequest;
    private ChartRepository chartRepository;
    private final static String CSS_SELECTOR = "svg";

    public void execute(List<Chart> charts) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Set<Future<ScrapeResponse>> set = new HashSet<>();
        charts.forEach(
                (chart) -> {
                    Future<ScrapeResponse> scrapeResponse = executorService.submit(scrapeRequest.createRequest(chart.getSourceUri(), CSS_SELECTOR));
                    set.add(scrapeResponse);
                });
        executorService.shutdown();
        saveWebScrapes(set);
    }

    private void saveWebScrapes(Set<Future<ScrapeResponse>> set) {
        List<Chart> scrapedCharts = Lists.newArrayList();
        set.forEach(
                chartFuture -> {
                    try {
                        ScrapeResponse scrapeResponse = chartFuture.get();
                        String symbol = chartRepository.findBySourceUri(scrapeResponse.getUri()).getSymbol();
                        Chart chart = new Chart(symbol, scrapeResponse.getUri(), scrapeResponse.getContents());
                        scrapedCharts.add(chart);
                    } catch (InterruptedException e) {
                        Application.logger.error("Error while saving web scrapes", e);
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        Application.logger.error("Error while saving web scrapes", e);
                    }
                }
        );
        chartRepository.saveAll(scrapedCharts);
    }
}
