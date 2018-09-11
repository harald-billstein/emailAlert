package com.woodtailer.emailalertplugin.model;

import lombok.Data;

@Data
public class WordResponse {

  private String word;
  private boolean success;
  private String message;

}
