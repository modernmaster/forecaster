package uk.co.jamesmcguigan.forecaster.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import static uk.co.jamesmcguigan.forecaster.ui.configuration.WebSocketConfiguration.MESSAGE_PREFIX;

@Controller
@RequiredArgsConstructor
public class StompJsController {

    private final SimpMessagingTemplate websocket;

    @PostMapping("/stock-list")
    @SendTo("/topic/updateStock")
    @ResponseBody
    public ResponseEntity<Object> updateStock(@RequestBody String id) {

        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/updateStock", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}
