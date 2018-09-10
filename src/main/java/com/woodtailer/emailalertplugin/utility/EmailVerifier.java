package com.woodtailer.emailalertplugin.utility;


public class EmailVerifier {

  public static boolean validateMailaddress(String mailaddress) {

    //TODO WORST EMAIL CHECKER EVER! FIX!!
    return mailaddress.contains("@");
  }

}
