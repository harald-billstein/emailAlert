package com.woodtailer.emailalertplugin.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Subscriber")
public class Subscriber {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long subscriberID;

  @Column(unique = true)
  private String emailAddress;
  private long mailSentToSubscriber;
  private Date date;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "Subscriber_TriggerWord",
      joinColumns = @JoinColumn(name = "subscriberID"),
      inverseJoinColumns = @JoinColumn(name = "triggerWordID")
  )
  private Set<TriggerWord> triggerWords = new HashSet<>();

  public long getSubscriberID() {
    return subscriberID;
  }

  public void setSubscriberID(Long subscriberID) {
    this.subscriberID = subscriberID;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public long getMailSentToSubscriber() {
    return mailSentToSubscriber;
  }

  public void setMailSentToSubscriber(long mailSentToSubscriber) {
    this.mailSentToSubscriber = mailSentToSubscriber;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Set<TriggerWord> getTriggerWords() {
    return triggerWords;
  }

  public void setTriggerWords(
      Set<TriggerWord> triggerWords) {
    this.triggerWords = triggerWords;
  }
}
