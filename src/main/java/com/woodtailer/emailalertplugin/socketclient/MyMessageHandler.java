package com.woodtailer.emailalertplugin.socketclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


@Service
public class MyMessageHandler extends TextWebSocketHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyMessageHandler.class);

  private MyMessageHandlerInterface listener;

  public void setListener(MyMessageHandlerInterface listener) {
    this.listener = listener;
  }

  private WebSocketSession session;

  public void connect(String url) {
    List<Transport> transports = new ArrayList<>(2);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    transports.add(new RestTemplateXhrTransport());

    SockJsClient sockJsClient = new SockJsClient(transports);
    sockJsClient.doHandshake(this, url);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.session = session;
    LOGGER.info("Sending hello from Email Alert");
    session.sendMessage(new TextMessage("Hello from Email Alert"));
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    LOGGER.info("handleMessage : " + message.getPayload());
    listener.update(String.valueOf(message.getPayload()));
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    LOGGER.info("handleTextMessage");
  }

  @Override
  protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
    LOGGER.info("handlePongMessage");
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    LOGGER.info("handleTransportError");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    LOGGER.info("afterConnectionClosed");
  }

  @Override
  public boolean supportsPartialMessages() {
    LOGGER.info("supportsPartialMessages");
    return super.supportsPartialMessages();
  }

  public boolean sendMessage(String message) {
    boolean success;

    try {
      session.sendMessage(new TextMessage(message));
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
      success = false;
    }
    return success;
  }

}
