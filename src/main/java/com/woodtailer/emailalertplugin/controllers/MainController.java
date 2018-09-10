package com.woodtailer.emailalertplugin.controllers;

import com.woodtailer.emailalertplugin.mailservice.MailService;
import com.woodtailer.emailalertplugin.model.Subscriber;
import com.woodtailer.emailalertplugin.model.TriggerWord;
import com.woodtailer.emailalertplugin.persistentstorstorage.SubscriberRepository;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandler;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandlerInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class MainController implements MyMessageHandlerInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
  private final MailService mailService;
  private final MyMessageHandler myMessageHandler;
  private SubscriberRepository subscriberRepository;


  public MainController(SubscriberRepository subscriberRepository, MailService mailService,
      MyMessageHandler myMessageHandler) {
    this.mailService = mailService;
    this.myMessageHandler = myMessageHandler;
    this.subscriberRepository = subscriberRepository;
    myMessageHandler.setListener(this);
  }

  public void start() {
    LOGGER.info("CONNECTING TO SOCKET");
    myMessageHandler.connect();
  }

  @Override
  public void update(String s) {
    LOGGER.info("MainController send method: " + s);

    Iterable<Subscriber> tempUsers = subscriberRepository.findAll();
    Iterator<Subscriber> users = tempUsers.iterator();
    List<String> mailaddressesToAlert = new ArrayList<>();

    while (users.hasNext()) {
      Subscriber subscriber = users.next();
      Set<TriggerWord> tempWords = subscriber.getTriggerWords();

      for (TriggerWord tempWord : tempWords) {
        if (s.contains(tempWord.getWord())) {
          mailaddressesToAlert.add(subscriber.getEmailAddress());
          mailService.sendMailToSubscribers(s, mailaddressesToAlert);
        }
      }
    }
  }
}
