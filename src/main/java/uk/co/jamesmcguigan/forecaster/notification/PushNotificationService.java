package uk.co.jamesmcguigan.forecaster.notification;

import uk.co.jamesmcguigan.forecaster.notification.fcm.FcmClient;
import uk.co.jamesmcguigan.forecaster.stock.StockAlert;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

  private final FcmClient fcmClient;

  private int seq = 0;

  public PushNotificationService(FcmClient fcmClient) {
    this.fcmClient = fcmClient;
  }

  public void sendPushMessage(StockAlert stockAlert) throws InterruptedException, ExecutionException {
    Map<String, String> data = new HashMap<>();
    data.put("id", String.valueOf(stockAlert.getStock().getSymbol()));
    data.put("stock", stockAlert.getMessage());
    data.put("seq", String.valueOf(this.seq++));
    data.put("ts", String.valueOf(System.currentTimeMillis()));

    System.out.println("Sending stock alert...");
    this.fcmClient.send(data);
  }

}
