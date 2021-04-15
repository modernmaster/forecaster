package uk.co.jamesmcguigan.forecaster.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockApiClient {
    public static final String SYMBOL = "symbol";
    private static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_ID = "Status code {} received for stock update request for id {}";
    private static final String STOCK_SERVICE = "stock-service";
    private static final String PATCH_PATH = "/api/stocks/%s";
    private static final String SYMBOL_SEARCH_PATH = "/api/stocks/search/findBySymbol";
    private final WebClient webClient;
    private final DiscoveryClient discoveryClient;

    public void patch(String id, TrendRepresentation trendRepresentation) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        String path = String.format(PATCH_PATH, id);
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(path)
                        .build();
        this.webClient.patch().uri(uriBuilder.toUri()).body(Mono.just(trendRepresentation), TrendRepresentation.class).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_ID, x.statusCode(), id);
                    }
                    return x.bodyToMono(String.class);
                }).block();
    }

    public Stock get(String symbol) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(SYMBOL_SEARCH_PATH)
                        .queryParam(SYMBOL, symbol)
                        .build();
        return this.webClient.get().uri(uriBuilder.toUri()).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_ID, x.statusCode(), symbol);
                    }
                    return x.bodyToMono(Stock.class);
                }).block();
    }
}
