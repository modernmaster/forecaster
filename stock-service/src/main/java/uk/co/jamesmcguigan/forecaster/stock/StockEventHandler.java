package uk.co.jamesmcguigan.forecaster.stock;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@RepositoryEventHandler(Stock.class)
public class StockEventHandler {

    private static final Logger log = LoggerFactory.getLogger(StockEventHandler.class);
    private static final String UI_API_ADDR = "UI_API_ADDR";
    private static final String S_UPDATE_STOCK_S = "http://%s/stock-list";
    private static final String XX_CLIENT_ERROR_THROWN_WHEN_COMMUNICATING_WITH_S = "5xx client error thrown when communicating with %s";
    private static final String XX_CLIENT_ERROR_THROWN_WHEN_COMMUNICATING_WITH_S1 = "4xx client error thrown when communicating with %s";
    private final EntityLinks entityLinks;
    private final WebClient webClient;

    @HandleAfterCreate
    public void createStock(Stock stock) {
        sendRequest(stock);
    }

    private void sendRequest(Stock stock) {
        String apiAddress = System.getenv(UI_API_ADDR);
        String apiUri = String.format(S_UPDATE_STOCK_S, apiAddress);
        HttpStatus httpStatus =
                Objects.requireNonNull(webClient
                        .post()
                        .uri(apiUri)
                        .body(Mono.just(getPath(stock)), String.class)
                        .exchange()
                        .block())
                        .statusCode();
        log.info(apiUri + " returned " + httpStatus);
        if (httpStatus.is4xxClientError()) {
            throw new BadRequestForStompUpdate(String.format(XX_CLIENT_ERROR_THROWN_WHEN_COMMUNICATING_WITH_S1, apiUri));
        } else if (httpStatus.is5xxServerError()) {
            throw new ServerUnavailableForStompUpdate(String.format(XX_CLIENT_ERROR_THROWN_WHEN_COMMUNICATING_WITH_S, apiUri));
        }
    }

    private String getPath(Stock stock) {
        return this.entityLinks.linkForSingleResource(stock.getClass(),
                stock.getId()).toUri().getPath();
    }

    @HandleAfterSave
    public void updateStock(Stock stock) {
        sendRequest(stock);

    }

}