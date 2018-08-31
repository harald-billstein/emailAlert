package com.woodtailer.emailalertplugin.utility;


import com.woodtailer.emailalertplugin.mailservice.EmailSubscribers;

public class EmailVerifier {

  public static boolean validateMailaddress(String mailaddress) {

    //TODO WORST EMAIL CHECKER EVER! FIX!!
    return mailaddress.contains("@");
  }

  public static boolean isDuplicate(String mailaddress, EmailSubscribers emailSubscribers) {

    return emailSubscribers.getAddresses().contains(mailaddress.toLowerCase());

  }

}
