package com.woodtailer.emailalertplugin.model;

import lombok.Data;

@Data
public class MailResponse {

  private String registeredMail;
  private boolean success;
  private String message;

}
