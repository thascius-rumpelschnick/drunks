package org.kappa.server.socket.controller;

import org.kappa.server.socket.model.Message;
import org.kappa.server.socket.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GameController {

  @MessageMapping("/game")
  @SendTo("/topic/message")
  public OutputMessage send(final Message message) {

    final String time = new SimpleDateFormat("HH:mm").format(new Date());
    return new OutputMessage(message.from(), message.text(), time);
  }

}
