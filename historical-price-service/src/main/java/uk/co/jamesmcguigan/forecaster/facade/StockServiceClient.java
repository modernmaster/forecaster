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
import uk.co.jamesmcguigan.forecaster.facade.request.HistoricalPriceRepresentation;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockServiceClient {

    public static final String ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL = "Status code {} received for historical price update request for symbol {}";
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

//    public List<Stock> get(String symbol, MultiValueMap<String, String> queryParams) {
//        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
//        String path = String.format(PATH, symbol);
//        UriComponents uriBuilder =
//                UriComponentsBuilder.fromUri(stockServiceUrl)
//                        .path(path)
//                        .queryParams(queryParams)
//                        .build();
//        return this.webClient.get().uri(uriBuilder.toUri()).exchangeToMono(
//                x -> {
//                    if (!x.statusCode().equals(HttpStatus.OK)) {
//                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, x.statusCode(), symbol);
//                    }
//                    return x.bodyToMono(new ParameterizedTypeReference<>() {
//                    });
//                }).block();
//    }
//
//


//    public List<Stock> get(MultiValueMap<String, String> queryParams) {
//        URI stockServiceUrl = discoveryClient.getInstances(STOCK_SERVICE).get(0).getUri();
//        UriComponents uriBuilder =
//                UriComponentsBuilder.fromUri(stockServiceUrl)
//                        .path(PATH)
////                        .queryParam(PAGE, Integer.toString(page))
////                        .queryParam(SIZE, Integer.toString(size))
//                        .build();
//        this.webClient.patch().uri(uriBuilder.toUri()).body(Mono.just(historicalPrices), HistoricalPriceRepresentation.class).exchangeToMono(
//                x -> {
//                    if (!x.statusCode().equals(HttpStatus.OK)) {
//                        log.error(ERROR_RECEIVED_FOR_STOCK_UPDATE_REQUEST_FOR_SYMBOL, x.statusCode(), symbol);
//                    }
//                    return x.bodyToMono(String.class);
//                }).block();
}
