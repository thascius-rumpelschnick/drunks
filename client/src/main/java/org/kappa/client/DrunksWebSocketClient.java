package org.kappa.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class DrunksWebSocketClient {

    private static final Logger LOG = LoggerFactory.getLogger(DrunksWebSocketClient.class);

    private static final String SERVER_URI = "ws://localhost:5555/";

    private WebSocketClient client;

    public void startConnection() {
        try {
            client = new WebSocketClient(new URI(SERVER_URI)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    LOG.debug("Connected to WebSocket server");
                }

                @Override
                public void onMessage(String message) {
                    LOG.debug("Received from server: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    LOG.debug("Connection closed");
                }

                @Override
                public void onError(Exception ex) {
                    LOG.debug("Error occurred", ex);
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            LOG.debug("Invalid server URI", e);
        }
    }

    public void sendMessage(String message) {
        if (client != null && client.isOpen()) {
            client.send(message);
        }
    }

    public void stopConnection() {
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) {
        DrunksWebSocketClient client = new DrunksWebSocketClient();
        client.startConnection();
        client.sendMessage("Hello");
        client.sendMessage("World");
        client.sendMessage(".");
        client.stopConnection();
    }
}
