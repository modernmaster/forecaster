package uk.co.jamesmcguigan.forecaster.stock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static uk.co.jamesmcguigan.forecaster.WebSocketConfiguration.MESSAGE_PREFIX;

@Component
@RepositoryEventHandler(StockRepository.class)
public class StockEventHandler {

    private final SimpMessagingTemplate websocket;

    private final EntityLinks entityLinks;

    @Autowired
    public StockEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
    }

    @HandleAfterCreate
    public void newstock(Stock stock) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/newStock", getPath(stock));
    }

    @HandleAfterDelete
    public void deletestock(Stock stock) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/deletestock", getPath(stock));
    }

    @HandleAfterSave
    public void updatestock(Stock stock) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/updatestock", getPath(stock));
    }

    private String getPath(Stock stock) {
        return this.entityLinks.linkForSingleResource(stock.getClass(),
                stock).toUri().getPath();
    }

}