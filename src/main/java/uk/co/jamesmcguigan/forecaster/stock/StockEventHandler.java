package uk.co.jamesmcguigan.forecaster.stock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(StockRepository.class)
public class StockEventHandler {

  private static final Logger log = LoggerFactory.getLogger(StockEventHandler.class);
  private final SimpMessagingTemplate websocket;
  private final EntityLinks entityLinks;

  @Autowired
  public StockEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
    this.websocket = websocket;
    this.entityLinks = entityLinks;
  }

//    @HandleAfterCreate
//    public void newstock(Stock stock) {
//        this.websocket.convertAndSend(
//                MESSAGE_PREFIX + "/newStock", getPath(stock));
//    }
//
//    @HandleAfterDelete
//    public void deletestock(Stock stock) {
//        this.websocket.convertAndSend(
//                MESSAGE_PREFIX + "/deletestock", getPath(stock));
//    }
//
//    @HandleAfterSave
//    public void updatestock(Stock stock) {
//        log.error("Update"+stock.getCompanyName());
//        this.websocket.convertAndSend(
//                MESSAGE_PREFIX + "/updatestock/"+stock.getSymbol());
//    }

}