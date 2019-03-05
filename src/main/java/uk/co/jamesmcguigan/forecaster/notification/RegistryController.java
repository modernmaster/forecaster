package uk.co.jamesmcguigan.forecaster.notification;


import reactor.core.publisher.Mono;
import uk.co.jamesmcguigan.forecaster.notification.fcm.FcmClient;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RegistryController {

  private final FcmClient fcmClient;

  public RegistryController(FcmClient fcmClient) {
    this.fcmClient = fcmClient;
  }

  @PostMapping("/register")
  public void register(@RequestBody Mono<String> token) {
    token.subscribe(t -> this.fcmClient.subscribe("chuck", t));
  }

}
