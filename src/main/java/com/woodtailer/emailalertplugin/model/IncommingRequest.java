package com.woodtailer.emailalertplugin.model;

import java.util.ArrayList;
import lombok.Data;

@Data
public class IncommingRequest {

  private String emailAddress;

  private ArrayList words;

}
