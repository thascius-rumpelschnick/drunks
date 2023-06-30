package org.kappa.server.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kappa.server.socket.model.Message;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


public class PlainWebsocketHandler extends TextWebSocketHandler {

  private final ObjectMapper mapper;


  public PlainWebsocketHandler(final ObjectMapper mapper) {
    this.mapper = mapper;
  }


  @Override
  public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws IOException {
    final var payload = message.getPayload();
    final var msg = this.mapper.readValue(payload, Message.class);

    session.sendMessage(new TextMessage("Hi " + msg.from() + " how may we help you?"));
  }

}
