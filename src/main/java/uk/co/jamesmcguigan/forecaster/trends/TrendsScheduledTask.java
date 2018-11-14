package uk.co.jamesmcguigan.forecaster.trends;

import uk.co.jamesmcguigan.forecaster.Application;
import uk.co.jamesmcguigan.forecaster.notification.PushNotificationService;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class TrendsScheduledTask {

  private PushNotificationService pushNotificationService;

  @Autowired
  public TrendsScheduledTask(
      PushNotificationService pushNotificationService) {
    this.pushNotificationService = pushNotificationService;
  }

  @Scheduled(fixedRate = 30000)
  public void process() {
    String message = "My alert has fired";
    StockAlert stockAlert = new StockAlert(new Stock(), message);
    try {

      pushNotificationService.sendPushMessage(stockAlert);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      Application.logger.error("Error in sending notification", e);
    }
  }

}
