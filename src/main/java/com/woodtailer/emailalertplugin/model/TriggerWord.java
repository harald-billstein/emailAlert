package com.woodtailer.emailalertplugin.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TriggerWord")
public class TriggerWord {

  @Id()
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long triggerWordID;

  private String word;

  @ManyToMany(mappedBy = "triggerWords", fetch = FetchType.EAGER)
  private Set<Subscriber> subscribers = new HashSet<>();

  public long getTriggerWordID() {
    return triggerWordID;
  }

  public void setTriggerWordID(Long triggerWordID) {
    this.triggerWordID = triggerWordID;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public Set<Subscriber> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(Set<Subscriber> subscribers) {
    this.subscribers = subscribers;
  }
}