package com.woodtailer.emailalertplugin.utility;


import com.woodtailer.emailalertplugin.model.Subscriber;

public class EmailUtil {

  public static boolean validateMailaddress(String mailaddress) {

    //TODO WORST EMAIL CHECKER EVER! FIX!!
    return mailaddress.contains("@");
  }

  public static boolean userBeenSpammed(Subscriber subscriber) {
    long timeStamp = subscriber.getTimeStamp();
    return timeStamp > (System.currentTimeMillis() - 1800000);
  }

}
