package com.woodtailer.emailalertplugin.persistentstorstorage;

import com.woodtailer.emailalertplugin.model.Subscriber;
import java.util.Iterator;
import org.springframework.stereotype.Service;

@Service
public class SubscriberRepositoryIMP {

  private SubscriberRepository subscriberRepository;

  public SubscriberRepositoryIMP(SubscriberRepository subscriberRepository) {
    this.subscriberRepository = subscriberRepository;
  }

  public Subscriber getSubscriberByMail(String mail) {

    Iterator<Subscriber> subscribers = subscriberRepository.findAll().iterator();

    for (Iterator<Subscriber> it = subscribers; it.hasNext(); ) {
      Subscriber subscriber = it.next();

      if (subscriber.getEmailAddress().equalsIgnoreCase(mail.toLowerCase())) {
        return subscriber;
      }
    }
    return null;
  }

  public Iterable<Subscriber> getAllSubscribers() {
    return subscriberRepository.findAll();
  }

  public Subscriber saveSubscriber(Subscriber subscriber) {
    return subscriberRepository.save(subscriber);
  }

  public void deleteSubscriber(Subscriber subscriber) {
    subscriberRepository.delete(subscriber);
  }
}
