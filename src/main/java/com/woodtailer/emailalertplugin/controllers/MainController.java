package com.woodtailer.emailalertplugin.controllers;

import com.woodtailer.emailalertplugin.mailservice.MailService;
import com.woodtailer.emailalertplugin.model.Subscriber;
import com.woodtailer.emailalertplugin.model.TriggerWord;
import com.woodtailer.emailalertplugin.persistentstorstorage.SubscriberRepositoryIMP;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandler;
import com.woodtailer.emailalertplugin.socketclient.MyMessageHandlerInterface;
import com.woodtailer.emailalertplugin.utility.EmailUtil;
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
  private SubscriberRepositoryIMP subscriberRepositoryIMP;


  public MainController(SubscriberRepositoryIMP subscriberRepositoryIMP, MailService mailService,
      MyMessageHandler myMessageHandler) {
    this.mailService = mailService;
    this.myMessageHandler = myMessageHandler;
    this.subscriberRepositoryIMP = subscriberRepositoryIMP;
    myMessageHandler.setListener(this);
  }

  public void start() {
    LOGGER.info("CONNECTING TO SOCKET");
    myMessageHandler.connect();
  }


  // TODO CLEAN UP THIS MESS
  @Override
  public void update(String incomingMessage) {

    LOGGER.info("INCOMMING MESSAGE : " + incomingMessage);

    Iterable<Subscriber> tempUsers = subscriberRepositoryIMP.getAllSubscribers();
    Iterator<Subscriber> users = tempUsers.iterator();
    List<String> mailaddressesToAlert = new ArrayList<>();

    while (users.hasNext()) {
      Subscriber subscriber = users.next();
      Set<TriggerWord> tempWords = subscriber.getTriggerWords();

      for (TriggerWord tempWord : tempWords) {
        if (incomingMessage.contains(tempWord.getWord())) {
          mailaddressesToAlert.add(subscriber.getEmailAddress());

          if (!EmailUtil.userBeenSpammed(subscriber)) {
            mailService.sendMailToSubscribers(incomingMessage, mailaddressesToAlert);
            subscriber.setTimeStamp(System.currentTimeMillis());
            subscriberRepositoryIMP.saveSubscriber(subscriber);
            LOGGER.info("ALERT MAIL SENT TO : " + subscriber.getEmailAddress());
          } else {
            LOGGER.warn(subscriber.getEmailAddress() + " WAS ALERTED RECENTLY, SPAM PROTECTION");
          }
        }
      }
    }
  }
}
