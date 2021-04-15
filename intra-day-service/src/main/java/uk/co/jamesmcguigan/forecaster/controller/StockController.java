package uk.co.jamesmcguigan.forecaster.controller;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.jamesmcguigan.forecaster.service.stock.StockRetrievalService;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StockController {

    private static final String UPDATE_STOCK_EXCEPTION = "Update Stock Exception";
    private static final String PATCH_REQUEST_RECEIVED_FOR_ID = "PATCH request received for id {}";
    @Autowired
    private StockRetrievalService stockLookupService;

    @NotNull
    @PostMapping("/stocks")
    @ResponseBody
    public final ResponseEntity createStock() {
        HttpStatus httpStatus = update();
        return ResponseEntity.status(httpStatus).body(null);
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
            log.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            log.error(UPDATE_STOCK_EXCEPTION, ex);
            httpStatus = HttpStatus.I_AM_A_TEAPOT;
        }
        return ResponseEntity.status(httpStatus).body(null);
    }

    //TODO: update prices for stocks.
    @NotNull
    @PutMapping("/stocks")
    @ResponseBody
    public final ResponseEntity updateStocks() {
        HttpStatus httpStatus = update();
        return ResponseEntity.status(httpStatus).body(null);
    }

    private HttpStatus update() {
        try {
            stockLookupService.updateStocks();
            return HttpStatus.CREATED;
        } catch (IllegalArgumentException ex) {
            log.error(UPDATE_STOCK_EXCEPTION, ex);
            return HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            log.error(UPDATE_STOCK_EXCEPTION, ex);
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }

}
