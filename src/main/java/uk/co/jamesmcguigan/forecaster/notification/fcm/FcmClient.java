package uk.co.jamesmcguigan.forecaster.notification.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.Application;
import uk.co.jamesmcguigan.forecaster.notification.FcmSettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class FcmClient {

    private static final String TOKENS_WERE_SUBSCRIBED_SUCCESSFULLY = " tokens were subscribed successfully";

    public FcmClient(FcmSettings settings) throws IOException {
        boolean hasBeenInitialized = false;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        for (FirebaseApp app : firebaseApps) {
            if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                hasBeenInitialized = true;
            }
        }
        if (!hasBeenInitialized) {
            createFirebaseInstance(settings);
        }
    }

    private void createFirebaseInstance(FcmSettings settings) throws IOException {
        Path p = Paths.get(settings.getServiceAccountFile());
        InputStream inputStream =
                this.getClass().getResourceAsStream(
                        "/forecaster-push-notifications-firebase-adminsdk-w8xjr-61e90f0ce9.json");
        if (inputStream == null) {
            Application.logger.error("init fcm", new FileNotFoundException());
            return;
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        FirebaseApp.initializeApp(options);
    }


    public void send(Map<String, String> data)
            throws InterruptedException, ExecutionException {
        String id = data.get("id");
        String stockMessage = data.get("stock");
        Message message = Message.builder().putAllData(data).setTopic("chuck")
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(id,
                                stockMessage, "mail2.png"))
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Sent message: " + response);
    }

    public void subscribe(String topic, String clientToken) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopicAsync(Collections.singletonList(clientToken), topic).get();
            Application.logger.info(response.getSuccessCount() + TOKENS_WERE_SUBSCRIBED_SUCCESSFULLY);
        } catch (InterruptedException | ExecutionException e) {
            Application.logger.error("subscribe", e);
        }
    }
}
