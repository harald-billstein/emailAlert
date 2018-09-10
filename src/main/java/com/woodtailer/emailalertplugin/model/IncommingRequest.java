package com.woodtailer.emailalertplugin.model;

import java.util.ArrayList;

public class IncommingRequest {

  private String emailAddress;

  private ArrayList words;

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public ArrayList getWords() {
    return words;
  }

  public void setWords(ArrayList words) {
    this.words = words;
  }
}
