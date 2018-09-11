package com.woodtailer.emailalertplugin.socketclient;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${socket.url}")
  private String url;

  public void setListener(MyMessageHandlerInterface listener) {
    this.listener = listener;
  }

  public void connect() {
    List<Transport> transports = new ArrayList<>(2);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    transports.add(new RestTemplateXhrTransport());

    SockJsClient sockJsClient = new SockJsClient(transports);
    sockJsClient.doHandshake(this, url);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    session.sendMessage(new TextMessage("EMAIL ALERTER CONNECTED"));
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
    //LOGGER.info("handleMessage : " + message.getPayload());
    listener.update(String.valueOf(message.getPayload()));
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    LOGGER.info("handleTextMessage");
  }

  @Override
  protected void handlePongMessage(WebSocketSession session, PongMessage message) {
    LOGGER.info("handlePongMessage");
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    LOGGER.info("handleTransportError");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    LOGGER.info("afterConnectionClosed");
  }

  @Override
  public boolean supportsPartialMessages() {
    LOGGER.info("supportsPartialMessages");
    return super.supportsPartialMessages();
  }

}
