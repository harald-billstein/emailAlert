package com.woodtailer.emailalertplugin.controllers;

import com.woodtailer.emailalertplugin.mailservice.EmailSubscribers;
import com.woodtailer.emailalertplugin.mailservice.MailService;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandler;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class MainController implements MyMessageHandlerInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

  private final EmailSubscribers subscribers;
  private final MailService mailService;
  private final MyMessageHandler myMessageHandler;

  @Value("${socket}")
  private String url;

  public MainController(MailService mailService, EmailSubscribers subscribers,
      MyMessageHandler myMessageHandler) {
    this.mailService = mailService;
    this.subscribers = subscribers;
    this.myMessageHandler = myMessageHandler;
    myMessageHandler.setListener(this);
  }

  public void startApplication() {
    myMessageHandler.connect(url);
  }


  private void sendMail(String message) {


    if (subscribers.getAddresses().size() == 0) {
      LOGGER.info("NO SUBSCRIBERS TO NOTIFY");
    } else {
      mailService.sendMailToSubscribers(message, subscribers.getAddresses());
    }
  }

  @Override
  public void update(String s) {
    if (s.equals("WARN") || s.equals("ERROR")) {
      sendMail(s);
    }
  }
}
