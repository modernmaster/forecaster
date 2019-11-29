package uk.co.jamesmcguigan.forecaster.stock;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;
import uk.co.jamesmcguigan.forecaster.stock.liveprice.GoogleSheetRepresentation;

@Service
@RequiredArgsConstructor
public class StockLookupService extends HttpServlet implements StockLookup {

    private final StockTransformer stockTransformer;
    @Autowired
    private final WebClient webClient;
    private final StockRepository stockRepository;
    private static final String STOCK_LISTING_UPDATED = "Stock listing updated";
    private static final Logger log = LoggerFactory.getLogger(StockLookupService.class);
    private static final String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";

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

    public Page<Stock> getStocks(Pageable pageable) {
        return stockRepository.findAll(pageable);
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
    public Stock createStock(String symbol) {
        log.info("Create: {}" + symbol);
        List<Stock> stocks = getStocksFromResource();
        Stock stock = StreamSupport.stream(stocks.spliterator(), false)
                .filter(s -> !s.getPercentageChange().equals("#N/A"))
                .findFirst().orElseThrow(() -> new StockNotFoundException(String.format("Stock not in returned collection for: %s", symbol)));
        return stockRepository.save(stock);
    }

    @Override
    public void createStocks() {
        log.info("Create all stocks");
        List<Stock> stocks = getStocksFromResource();
        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
                .collect(Collectors.toList());
        stockRepository.saveAll(filteredStocks);
    }

    @Override
    public void updateStocks() {
        List<Stock> stocks = getStocksFromResource();
        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
                .collect(Collectors.toList());
        List<Stock> mergedStocks = mergeTrendsAndHistoricalPrices(filteredStocks);
        stockRepository.saveAll(mergedStocks);
    }

    private List<Stock> mergeTrendsAndHistoricalPrices(List<Stock> stocks) {
        stocks.forEach(stock-> {
            Stock s = stockRepository.findBySymbol(stock.getSymbol());
            stock.setTrends(s.getTrends());
            stock.setHistoricalPrices(s.getHistoricalPrices());
        });
        return stocks;
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void updateStock(String id) {
        List<Stock> stocks = getStocksFromResource();
        Stock stock = StreamSupport.stream(stocks.spliterator(), false)
                .filter(s -> !s.getPercentageChange().equals("#N/A"))
                .findFirst().get();
        log.info("Update:" + stock.getCompanyName());
        this.stockRepository.save(stock);
    }

    private List<Stock> getStocksFromResource() {
        GoogleSheetRepresentation googleSheetRepresentation = webClient
                .get()
                .uri(apiUrl)
                .retrieve().bodyToFlux(GoogleSheetRepresentation.class)
                .blockFirst();
        return stockTransformer.transform(googleSheetRepresentation);
    }

    private Double getPercentageChange(Stock stock) {
        return stock.getPercentageChange();
//        try {
//            return Double.parseDouble(stock.getPercentageChange());
//        } catch (NumberFormatException ex) {
//            return Double.MIN_VALUE;
//        }
    }
}
