package com.woodtailer.emailalertplugin.persistentstorstorage;

import com.woodtailer.emailalertplugin.model.Subscriber;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberRepository extends CrudRepository<Subscriber,Integer> {

}
