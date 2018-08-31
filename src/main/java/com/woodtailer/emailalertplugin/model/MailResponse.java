package com.woodtailer.emailalertplugin.model;

public class MailResponse {

  private String registeredMail;
  private boolean success;
  private String message;

  public String getRegisteredMail() {
    return registeredMail;
  }

  public void setRegisteredMail(String registeredMail) {
    this.registeredMail = registeredMail;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
