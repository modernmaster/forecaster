package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockTransformer stockTransformer;
    @Autowired
    RestTemplate restTemplate;

    private String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";

    public StockService() {
        stockTransformer = new StockTransformer();
    }

    public List<Stock> getStocks() {
        GoogleSheetRepresentation googleSheetRepresentation = restTemplate.getForObject(apiUrl, GoogleSheetRepresentation.class);
        return stockTransformer.transform(googleSheetRepresentation);
    }

    public List<Stock> getTop20HighestGrowthStocks() {
        GoogleSheetRepresentation googleSheetRepresentation = restTemplate.getForObject(apiUrl, GoogleSheetRepresentation.class);
        List<Stock> stocks = stockTransformer.transform(googleSheetRepresentation);
        return stocks.stream()
                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
                .sorted((o1, o2) -> {
                    Double percentO1 = getPercentageChange(o1);
                    Double percentO2 = getPercentageChange(o2);
                    if (percentO1 > percentO2) return -1;
                    if (percentO1 < percentO2) return 1;
                    return 0;
                }).collect(Collectors.toList());
    }

    private Double getPercentageChange(Stock stock) {
        try {
            return Double.parseDouble(stock.getPercentageChange());
        } catch (NumberFormatException ex) {
            return Double.MIN_VALUE;
        }
    }
}
