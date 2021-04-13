package uk.co.jamesmcguigan.forecaster.historicalprice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import uk.co.jamesmcguigan.forecaster.dto.HistoricalPriceRepresentation;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockServiceClient {

    public static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL = "Error received for historical price update request for symbol {}";
    private static final String STOCK_SERVICE = "stock-service";
    private static final String PATH = "/api";
    private final WebClient webClient;
    private final DiscoveryClient discoveryClient;

    public void post(String symbol, HistoricalPriceRepresentation historicalPrices) {
        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
        UriComponents uriBuilder =
                UriComponentsBuilder.fromUri(stockServiceUrl)
                        .path(PATH)
//                        .queryParam(PAGE, Integer.toString(page))
//                        .queryParam(SIZE, Integer.toString(size))
                        .build();
        this.webClient.post().uri(uriBuilder.toUri()).body(Mono.just(historicalPrices), HistoricalPriceRepresentation.class).exchangeToMono(
                x -> {
                    if (!x.statusCode().equals(HttpStatus.OK)) {
                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, symbol);
                    }
                    return x.bodyToMono(String.class);
                });
    }
}
