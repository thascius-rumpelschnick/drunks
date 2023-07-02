package org.kappa.server.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.kappa.server.socket.handler.PlainWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class PlainWebsocketConfig implements WebSocketConfigurer {

  @Override
  public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
    registry.addHandler(this.plainWebsocketHandler(), "/plain");
  }


  @Bean
  public PlainWebsocketHandler plainWebsocketHandler() {
    return new PlainWebsocketHandler(new JsonMapper());
  }

}
