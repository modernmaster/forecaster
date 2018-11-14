package uk.co.jamesmcguigan.forecaster.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.servlet.http.HttpServlet;

@Component
public class StockScheduledTask extends HttpServlet {

  private static final String STOCK_LISTING_UPDATED = "Stock listing updated";
  private static final Logger log = LoggerFactory.getLogger(StockScheduledTask.class);
  private final StockService stockService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  public StockScheduledTask(StockService stockService,
      SimpMessagingTemplate simpMessagingTemplate) {
    this.stockService = stockService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @Scheduled(fixedRate = 30000)
  public void updateStockRepository() {
    List<Stock> stockList = stockService.getUpdatedStocks();
    stockList.forEach(this::updatestock);
    log.info(STOCK_LISTING_UPDATED);
  }

  public void updatestock(Stock stock) {
    log.info("Update:" + stock.getCompanyName());
    this.simpMessagingTemplate.convertAndSend(
        "/topic/updatestock", stock);
  }

}
