package com.woodtailer.emailalertplugin.handler;

import com.woodtailer.emailalertplugin.model.IncommingRequest;
import com.woodtailer.emailalertplugin.model.MailResponse;
import com.woodtailer.emailalertplugin.model.Subscriber;
import com.woodtailer.emailalertplugin.model.TriggerWord;
import com.woodtailer.emailalertplugin.model.WordResponse;
import com.woodtailer.emailalertplugin.persistentstorstorage.SubscriberRepositoryIMP;
import com.woodtailer.emailalertplugin.utility.EmailUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private SubscriberRepositoryIMP subscriberRepositoryIMP;

  public EndpointHandler(SubscriberRepositoryIMP subscriberRepositoryIMP) {
    this.subscriberRepositoryIMP = subscriberRepositoryIMP;
  }

  public MailResponse addEmailToSubscribers(IncommingRequest request) {

    MailResponse mailResponse = new MailResponse();
    Subscriber subscriber = new Subscriber();

    boolean isValidEmail = EmailUtil.validateMailaddress(request.getEmailAddress());

    if (isValidEmail) {
      subscriber.setEmailAddress(request.getEmailAddress().toLowerCase());
      subscriber.setMailSentToSubscriber(0);
      // INFO : -1800000 off set timestamp (spam protection).
      subscriber.setTimeStamp(System.currentTimeMillis() - 1800000);
      subscriber.setTriggerWords(extractWordsToASet(request.getWords()));
      try {
        subscriberRepositoryIMP.saveSubscriber(subscriber);
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

    for (String word : words) {
      TriggerWord triggerWord = new TriggerWord();
      triggerWord.setWord(word);
      setOfTriggerWords.add(triggerWord);
    }
    return setOfTriggerWords;
  }

  public MailResponse removeEmailAddress(String email) {

    MailResponse mailResponse = new MailResponse();

    Subscriber subscriber = subscriberRepositoryIMP.getSubscriberByMail(email);

    if (subscriber != null) {
      subscriberRepositoryIMP.deleteSubscriber(subscriber);
      mailResponse.setSuccess(true);
      mailResponse.setRegisteredMail(email);
      mailResponse.setMessage("EMAIL REMOVED");
    } else {
      LOGGER.info("USER NOT FOUND");
      mailResponse.setSuccess(false);
      mailResponse.setRegisteredMail(email);
      mailResponse.setMessage("EMAIL NOT FOUND");
    }

    return mailResponse;
  }


  public WordResponse addWatchWordToSubscriber(String email, String word) {
    WordResponse wordResponse = new WordResponse();
    Subscriber subscriber = subscriberRepositoryIMP.getSubscriberByMail(email);

    if (subscriber != null) {
      TriggerWord triggerWord = new TriggerWord();
      triggerWord.setWord(word);
      subscriber.getTriggerWords().add(triggerWord);
      subscriberRepositoryIMP.saveSubscriber(subscriber);
      wordResponse.setSuccess(true);
      wordResponse.setWord(word);
      wordResponse.setMessage("Word successfully added to watchlist");
    } else {
      wordResponse.setSuccess(false);
      wordResponse.setWord(word);
      wordResponse.setMessage("Failed to save " + word + " to watchlist");
    }

    return wordResponse;
  }

  public WordResponse removeWordFromSubscriber(String email, String word) {

    WordResponse wordResponse = new WordResponse();
    Subscriber subscriber = subscriberRepositoryIMP.getSubscriberByMail(email);

    Iterator<TriggerWord> triggerWords = subscriber.getTriggerWords().iterator();

    for (Iterator<TriggerWord> it = triggerWords; it.hasNext(); ) {
      TriggerWord triggerWord = it.next();

      if (triggerWord.getWord().equalsIgnoreCase(word)) {
        LOGGER.info("SUCCESS REMOVING");
        triggerWords.remove();
        subscriberRepositoryIMP.saveSubscriber(subscriber);
        wordResponse.setSuccess(true);
        wordResponse.setMessage(word + " was deleted from " + email + "s watchlist");
        wordResponse.setWord(word);
        return wordResponse;
      }

    }
    LOGGER.info("FAILED TO REMOVE");
    wordResponse.setSuccess(false);
    wordResponse.setMessage("Failed to remove " + word + " to " + email + "s watchlist");
    wordResponse.setWord(word);
    return wordResponse;
  }


}
