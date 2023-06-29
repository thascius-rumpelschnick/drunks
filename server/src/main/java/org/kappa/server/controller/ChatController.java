package org.kappa.server.controller;

import org.kappa.server.domain.Message;
import org.kappa.server.domain.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatController {

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public OutputMessage send(final Message message) throws Exception {

    final String time = new SimpleDateFormat("HH:mm").format(new Date());
    return new OutputMessage(message.getFrom(), message.getText(), time);
  }

}
