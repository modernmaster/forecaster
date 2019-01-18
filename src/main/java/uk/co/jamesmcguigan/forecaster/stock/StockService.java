package uk.co.jamesmcguigan.forecaster.stock;

import com.google.common.collect.Lists;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import uk.co.jamesmcguigan.forecaster.notification.PushNotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

  private final StockTransformer stockTransformer;
  private final StockRepository stockRepository;

  private final WebClient webClient;

  private String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";

  @Autowired
  public StockService(StockTransformer stockTransformer, WebClient webClient,
      StockRepository stockRepository) {
    this.stockTransformer = stockTransformer;
    this.webClient = webClient;
    this.stockRepository = stockRepository;
  }

  public List<Stock> getUpdatedStocks() {
    List<Stock> priceChangedStocks = Lists.newArrayList();
    List<Stock> currentStocksState = getStocks();
    List<Stock> stocks = Lists.newArrayList(stockRepository.findAll());

    if (stocks.size() > 0) {
      for (Stock stock : stocks) {
        Stock priceChangedStock =
            currentStocksState.stream().filter(
                x -> x.getCompanyName().equals(stock.getCompanyName()) && !x.equals(stock)
            ).findFirst().orElse(null);
        if (priceChangedStock != null) {
          priceChangedStocks.add(priceChangedStock);
        }
      }
      stockRepository.saveAll(priceChangedStocks);
      return priceChangedStocks;
    }
    stockRepository.saveAll(currentStocksState);
    return currentStocksState;
  }

  List<Stock> getStocks() {
    GoogleSheetRepresentation googleSheetRepresentation = webClient
            .get()
            .uri(apiUrl)
            .retrieve().bodyToFlux(GoogleSheetRepresentation.class)
            .blockFirst();

    List<Stock> stocks = stockTransformer.transform(googleSheetRepresentation);
    return stocks.stream()
        .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
        .sorted((o1, o2) -> {
          Double percentO1 = getPercentageChange(o1);
          Double percentO2 = getPercentageChange(o2);
          if (percentO1 > percentO2) {
            return -1;
          }
          if (percentO1 < percentO2) {
            return 1;
          }
          return 0;
        }).limit(100).collect(Collectors.toList());
  }

  private Double getPercentageChange(Stock stock) {
    try {
      return Double.parseDouble(stock.getPercentageChange());
    } catch (NumberFormatException ex) {
      return Double.MIN_VALUE;
    }
  }
}
