package uk.co.jamesmcguigan.forecaster.stock;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.stock.liveprice.GoogleSheetRepresentation;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class StockLookupService extends HttpServlet implements StockLookup {

    private final StockTransformer stockTransformer;
    private final WebClient webClient;
    private final StockRepository stockRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String STOCK_LISTING_UPDATED = "Stock listing updated";
    private static final Logger log = LoggerFactory.getLogger(StockLookupService.class);
    private static final String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";
    private final HistoricalPrice historicalPriceService;

    public List<Stock> getUpdatedStocks() {
        List<Stock> priceChangedStocks = Lists.newArrayList();
        List<Stock> currentStocksState = Lists.newArrayList(stockRepository.findAll());
        updateStocks();
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

    public List<Stock> getStocks() {
        Iterable<Stock> stocks = stockRepository.findAll();
        return StreamSupport.stream(stocks.spliterator(), false)
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

    @Override
    public void updateStocks() {
        GoogleSheetRepresentation googleSheetRepresentation = webClient
                .get()
                .uri(apiUrl)
                .retrieve().bodyToFlux(GoogleSheetRepresentation.class)
                .blockFirst();
        List<Stock> stocks = stockTransformer.transform(googleSheetRepresentation);
        stockRepository.saveAll(stocks);
    }

    @Override
    public void updateStocksAndNotifyAllClients() {
        List<Stock> stockList = getUpdatedStocks();
        stockList.forEach(this::updateStock);
        log.info(STOCK_LISTING_UPDATED);
    }

    public void updateStock(Stock stock) {
        log.info("Update:" + stock.getCompanyName());
        this.simpMessagingTemplate.convertAndSend(
                "/topic/updateStock", stock);
    }

    private Double getPercentageChange(Stock stock) {
        try {
            return Double.parseDouble(stock.getPercentageChange());
        } catch (NumberFormatException ex) {
            return Double.MIN_VALUE;
        }
    }
}
