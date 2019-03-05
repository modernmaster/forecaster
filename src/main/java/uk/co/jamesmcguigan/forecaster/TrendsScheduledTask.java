package uk.co.jamesmcguigan.forecaster;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.notification.PushNotificationService;

@Component
@AllArgsConstructor
public class TrendsScheduledTask {

    private PushNotificationService pushNotificationService;
//    private ChartScraperExecutor chartScraperExecutor;


    public void process() {
        //chartScraperExecutor.execute();

//    String message = "My alert has fired";
//    StockAlert stockAlert = new StockAlert(new Stock(), message);
//    try {
//
//      pushNotificationService.sendPushMessage(stockAlert);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } catch (ExecutionException e) {
//      Application.logger.error("Error in sending notification", e);
//    }
    }

}
