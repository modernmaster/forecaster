package uk.co.jamesmcguigan.forecaster.stock;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static uk.co.jamesmcguigan.forecaster.configuration.WebSocketConfiguration.MESSAGE_PREFIX;


@Component
@RepositoryEventHandler
@Slf4j
@RequiredArgsConstructor
public class StockEventHandler {

    private static final String SENDING_OUT_NOTIFICATION_TO_CLIENTS_FOR_STOCK = "Sending out notification to clients for stock {}";
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final EntityLinks entityLinks;

    @HandleAfterCreate
    @HandleAfterSave
    public void sendWebNotification(Stock stock) {
        log.debug(SENDING_OUT_NOTIFICATION_TO_CLIENTS_FOR_STOCK, stock.getSymbol());
        Map<String, String> stockProjection = buildMap(stock);
        simpMessagingTemplate.convertAndSend(MESSAGE_PREFIX + "/updateStock", stockProjection);
    }

    private Map<String, String> buildMap(Stock stock) {
        Map<String, String> stockProjection = Maps.newHashMap();
        stockProjection.put("symbol", stock.getSymbol());
        stockProjection.put("percentageChange", Double.toString(stock.getPercentageChange()));
        stockProjection.put("price", Double.toString(stock.getPrice()));
        stockProjection.put("volume", Double.toString(stock.getVolume()));
        return stockProjection;
    }

    private String getPath(Stock stock) {
        return this.entityLinks.linkForItemResource(stock.getClass(),
                stock.getId()).toUri().getPath();
    }
}