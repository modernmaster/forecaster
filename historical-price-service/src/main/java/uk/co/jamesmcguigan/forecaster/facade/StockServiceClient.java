package uk.co.jamesmcguigan.forecaster.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import uk.co.jamesmcguigan.forecaster.facade.request.HistoricalPriceRepresentation;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockServiceClient {

    public static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL = "Status code {} received for historical price update request for symbol {}";
    public static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_ALL_SYMBOLS = "Status code {} received for historical price update request for all symbols";
    private static final String STOCK_SERVICE = "stock-service";
    private static final String PATH = "/api/stocks/";
    private final WebClient webClient;
    private final DiscoveryClient discoveryClient;

    public void patch(String symbol, HistoricalPriceRepresentation historicalPrices) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        String path = PATH + symbol;
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(path)
                        .build();
        this.webClient.patch().uri(uriBuilder.toUri()).body(Mono.just(historicalPrices), HistoricalPriceRepresentation.class).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, x.statusCode(), symbol);
                    }
                    return x.bodyToMono(String.class);
                }).block();
    }

    public Mono<Stock> get(String symbol, MultiValueMap<String, String> queryParams) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        String path = PATH + symbol;
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(path)
                        .queryParams(queryParams)
                        .build();
        return this.webClient.get().uri(uriBuilder.toUri()).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, x.statusCode(), symbol);
                    }
                    return x.bodyToMono(new ParameterizedTypeReference<>() {
                    });
                });
    }

    public  Mono<PagedModel<Stock>> get(MultiValueMap<String, String> queryParams) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(PATH)
                        .queryParams(queryParams)
                        .build();
        return this.webClient.get().uri(uriBuilder.toUri()).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_ALL_SYMBOLS, x.statusCode());
                    }
                    return x.bodyToMono(new ParameterizedTypeReference<>() {
                    });
                });
    }
}
