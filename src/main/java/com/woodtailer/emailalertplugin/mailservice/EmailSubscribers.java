package com.woodtailer.emailalertplugin.mailservice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmailSubscribers {

  private final List<String> addresses = new ArrayList<>();

  private EmailSubscribers() {

  }

  public List<String> getAddresses() {
    return addresses;
  }

}
