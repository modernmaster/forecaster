package uk.co.jamesmcguigan.forecaster.stock;

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
class StockApiClient {
    public static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL = "Error received for stock update request for symbol {}";
    private static final String STOCK_SERVICE = "stock-service";
    private static final String PATH = "/api";
    private final WebClient webClient;
    private DiscoveryClient discoveryClient;

    public void post(Stock stock) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(PATH)
//                        .queryParam(PAGE, Integer.toString(page))
//                        .queryParam(SIZE, Integer.toString(size))
                        .build();
        this.webClient.post().uri(uriBuilder.toUri()).body(Mono.just(stock), Stock.class).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, stock.getSymbol());
                    }
                    return x.bodyToMono(String.class);
                });
    }
}
