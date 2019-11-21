package uk.co.jamesmcguigan.forecaster.controller;

import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.jamesmcguigan.forecaster.StockServiceApplication;
import uk.co.jamesmcguigan.forecaster.stock.StockLookupService;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPriceService;
import uk.co.jamesmcguigan.forecaster.stock.trend.TrendsService;

//https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration
@RequiredArgsConstructor
@RepositoryRestController
public class StockController {

    private static final String UPDATE_STOCK_EXCEPTION = "Update Stock Exception";
    private static final String PATCH_REQUEST_RECEIVED_FOR_ID = "PATCH request received for id {}";
    @Autowired
    @NonNull
    private StockLookupService stockLookupService;
    @Autowired
    @NonNull
    private HistoricalPriceService historicalPriceService;
    @Autowired
    @NonNull
    private TrendsService trendsService;

    @NotNull
    @PostMapping("/stocks")
    @ResponseBody
    public final ResponseEntity createStock() {
        try {
            stockLookupService.createStocks();
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception ex) {
            StockServiceApplication.logger.error(UPDATE_STOCK_EXCEPTION, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    //TODO: update prices for stock.
    @NotNull
    @PutMapping("/stocks/{id}")
    @ResponseBody
    public final ResponseEntity updateStock(@PathVariable String id) {
        Preconditions.checkNotNull(id);
        HttpStatus httpStatus;
        try {
            stockLookupService.updateStock(id.toUpperCase());
            httpStatus = HttpStatus.CREATED;
        } catch (IllegalArgumentException ex) {
            StockServiceApplication.logger.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            StockServiceApplication.logger.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.I_AM_A_TEAPOT;
        }
        return ResponseEntity.status(httpStatus).body(null);
    }

    //TODO: update prices for stocks.
    @NotNull
    @PutMapping("/stocks")
    @ResponseBody
    public final ResponseEntity updateStocks() {
        HttpStatus httpStatus;
        try {
            stockLookupService.updateStocks();
            httpStatus = HttpStatus.CREATED;
        } catch (IllegalArgumentException ex) {
            StockServiceApplication.logger.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            StockServiceApplication.logger.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.I_AM_A_TEAPOT;
        }
        return ResponseEntity.status(httpStatus).body(null);
    }

}
