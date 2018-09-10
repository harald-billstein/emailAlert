package com.woodtailer.emailalertplugin.handler;

import com.woodtailer.emailalertplugin.model.IncommingRequest;
import com.woodtailer.emailalertplugin.model.MailResponse;
import com.woodtailer.emailalertplugin.model.Subscriber;
import com.woodtailer.emailalertplugin.model.TriggerWord;
import com.woodtailer.emailalertplugin.persistentstorstorage.SubscriberRepository;
import com.woodtailer.emailalertplugin.utility.EmailVerifier;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private final SubscriberRepository subscriberRepository;

  public EndpointHandler(SubscriberRepository subscriberRepository) {
    this.subscriberRepository = subscriberRepository;
  }

  public MailResponse addEmailToSubscribers(IncommingRequest request) {

    MailResponse mailResponse = new MailResponse();
    Subscriber subscriber = new Subscriber();

    boolean isValidEmail = EmailVerifier.validateMailaddress(request.getEmailAddress());

    // TODO FIX THIS MESS

    if (isValidEmail) {
      subscriber.setEmailAddress(request.getEmailAddress());
      subscriber.setMailSentToSubscriber(0);
      subscriber.setDate(new Date(0));
      subscriber.setTriggerWords(extractWordsToASet(request.getWords()));
      try {
        subscriberRepository.save(subscriber);
        mailResponse.setMessage("ADDED TO SUBSCRIBERS");
        mailResponse.setRegisteredMail(request.getEmailAddress());
        mailResponse.setSuccess(true);
      } catch (Exception e) {
        mailResponse.setMessage("FAILED TO ADD SUBSCRIBER TO SERVICE");
        mailResponse.setRegisteredMail(request.getEmailAddress());
        mailResponse.setSuccess(false);
      }
    } else {
      mailResponse.setMessage("FAILED TO ADD SUBSCRIBER TO SERVICE");
      mailResponse.setRegisteredMail(request.getEmailAddress());
      mailResponse.setSuccess(false);
    }

    return mailResponse;
  }


  private Set<TriggerWord> extractWordsToASet(ArrayList<String> words) {

    Set<TriggerWord> setOfTriggerWords = new HashSet<>();

    for (int i = 0; i < words.size(); i++) {
      System.out.println("what" + words.get(i));
      TriggerWord triggerWord = new TriggerWord();
      triggerWord.setWord(words.get(i));
      setOfTriggerWords.add(triggerWord);

    }
    return setOfTriggerWords;
  }

  // TODO REMOVE EMAIL FROM DB

  // TODO CHECK WHEN LAST MAIL WAS SENT, MAX 1 MAIL EVERY 30 MIN


}
