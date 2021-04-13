package uk.co.jamesmcguigan.forecaster.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.dao.GoogleSheetRepresentation;
import uk.co.jamesmcguigan.forecaster.repository.LivePriceRepository;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePrice;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockRetrievalService extends HttpServlet implements StockRetrieval {

    private static final String STOCK_LISTING_UPDATED = "Stock listing updated";
    private static final String SAVING_LIVE_PRICES = "Saving {} live-prices";
    private static final String SENDING_LIVE_PRICES_TO_TRENDS_MESSAGE_QUEUE = "Sending {} live-prices to trends message queue";
    private static final String MAKING_UPDATING_STOCK_CALL = "Making updating stock {} call";
    private static final String apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mrRNCwJuvkeUoyGMs30Dubd9RGojdDwEfLVsK00gVvA/values/A1:R6500?key=AIzaSyCt9MonR8WBE0vsPoV_HBnB5k0-S3yhnZQ";
    private final TrendsMessageProducer trendsMessageProducer;
    private final StockTransformer stockTransformer;
    private final LivePriceTransformer livePriceTransformer;
    private final LivePriceRepository livePriceRepository;
    private final WebClient webClient;
    private final StockApiClient stockApiClient;

    @Override
    public void updateStocks() {
        List<Stock> stocks = getStocksFromResource();
        stocks.stream()
                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
                .forEach(this::processStock);
    }

    private List<Stock> getStocksFromResource() {
        GoogleSheetRepresentation googleSheetRepresentation = webClient
                .get()
                .uri(apiUrl)
                .retrieve().bodyToFlux(GoogleSheetRepresentation.class)
                .blockFirst();
        assert googleSheetRepresentation != null;
        return stockTransformer.transform(googleSheetRepresentation);
    }

    @Override
    public void updateStock(String id) {
        List<Stock> stocks = getStocksFromResource();
        stocks.stream()
                .filter(s -> s.getSymbol().equals(id))
                .filter(s -> !s.getPercentageChange().equals("#N/A"))
                .findFirst().ifPresent(this::processStock);
    }

    private void processStock(Stock stock) {
        log.debug(MAKING_UPDATING_STOCK_CALL, stock.getSymbol());
        stockApiClient.post(stock);
        LivePrice livePrice = livePriceTransformer.transform(stock);
        log.debug(SAVING_LIVE_PRICES, livePrice.getSymbol());
        livePriceRepository.save(livePrice);
        log.debug(SENDING_LIVE_PRICES_TO_TRENDS_MESSAGE_QUEUE, livePrice.getSymbol());
        trendsMessageProducer.sendMessage(livePrice);
    }

//    public List<Stock> getUpdatedStocks() {
//        List<Stock> priceChangedStocks = Lists.newArrayList();
//        List<Stock> currentStocksState = Lists.newArrayList(stockRepository.findAll());
//        updateStocks();
//        List<Stock> stocks = Lists.newArrayList(stockRepository.findAll());
//
//        if (stocks.size() > 0) {
//            for (Stock stock : stocks) {
//                Stock priceChangedStock =
//                        currentStocksState.stream().filter(
//                                x -> x.getCompanyName().equals(stock.getCompanyName()) && !x.equals(stock)
//                        ).findFirst().orElse(null);
//                if (priceChangedStock != null) {
//                    priceChangedStocks.add(priceChangedStock);
//                }
//            }
//            stockRepository.saveAll(priceChangedStocks);
//            return priceChangedStocks;
//        }
//        stockRepository.saveAll(currentStocksState);
//        return currentStocksState;
//    }
//
//    public Page<Stock> getStocks(Pageable pageable) {
//        return stockRepository.findAll(pageable);
//    }
//
//    private Double parseDouble(String value) {
//        try {
//            return Double.parseDouble(value);
//        } catch (NumberFormatException e) {
//            return 0d;
//        }
//    }
//
//    public List<Stock> getStocks() {
//        Iterable<Stock> stocks = stockRepository.findAll();
//        return StreamSupport.stream(stocks.spliterator(), false)
//                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
//                .sorted((o1, o2) -> {
//                    Double percentO1 = parseDouble(getPercentageChange(o1));
//                    Double percentO2 = parseDouble(getPercentageChange(o2));
//                    return percentO2.compareTo(percentO1);
//                }).limit(100).collect(Collectors.toList());
//    }
//
//    @Override
//    public Stock createStock(String symbol) {
//        log.info("Create: {}" + symbol);
//        List<Stock> stocks = getStocksFromResource();
//        Stock stock = StreamSupport.stream(stocks.spliterator(), false)
//                .filter(s -> !s.getPercentageChange().equals("#N/A"))
//                .findFirst().orElseThrow(() -> new StockNotFoundException(String.format("Stock not in returned collection for: %s", symbol)));
//        return stockRepository.save(stock);
//    }
//
//    @Override
//    public void createStocks() {
//        log.info("Create all stocks");
//        List<Stock> stocks = getStocksFromResource();
//        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
//                .filter(stock -> !stock.getPercentageChange().equals("#N/A"))
//                .collect(Collectors.toList());
//        stockRepository.saveAll(filteredStocks);
//    }


    //    private List<Stock> mergeTrendsAndHistoricalPrices(List<Stock> stocks) {
//        stocks.forEach(stock-> {
//            try {
//                Stock s = stockRepository.findBySymbol(stock.getSymbol());
//                stock.setTrends(s.getTrends());
//                stock.setHistoricalPrices(s.getHistoricalPrices());
//            } catch(NullPointerException ex) {
//                log.error("Missing trend data:{}", stock.getCompanyName());
//            }
//        });
//        return stocks;
//    }
//
//

//
//    private String getPercentageChange(Stock stock) {
//        return stock.getPercentageChange();
////        try {
////            return Double.parseDouble(stock.getPercentageChange());
////        } catch (NumberFormatException ex) {
////            return Double.MIN_VALUE;
////        }
//    }
}
