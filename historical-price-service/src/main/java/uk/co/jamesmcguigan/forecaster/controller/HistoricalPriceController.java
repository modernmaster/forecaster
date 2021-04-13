package uk.co.jamesmcguigan.forecaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.jamesmcguigan.forecaster.historicalprice.HistoricalService;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/request/")
public class HistoricalPriceController {

    public static final String REFRESH_ALL_HISTORICAL_PRICE_DATA = "Refresh all historical price data";
    private static final String HISTORICAL_PRICES = "historicalPrices";
    private static final String DATA_RECEIVED_FOR_WITH_ID = "Data received for {} with id {}";
    private final HistoricalService historicalService;

    @PostMapping("/{id}")
    public HttpStatus postHistoricalPriceRequest(@PathVariable @NotNull String id) {
        log.debug(DATA_RECEIVED_FOR_WITH_ID, HISTORICAL_PRICES, id);
        historicalService.submitHistoricalPriceRequest(id);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/")
    public HttpStatus postHistoricalPriceRequest() {
        log.debug(REFRESH_ALL_HISTORICAL_PRICE_DATA);
        historicalService.submitHistoricalPriceRequest();
        return HttpStatus.ACCEPTED;
    }

}
