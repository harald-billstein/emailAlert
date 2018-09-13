package com.woodtailer.emailalertplugin.utility;


import com.woodtailer.emailalertplugin.model.Subscriber;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailUtil {

  public static boolean validateMailaddress(String mailaddress) {

    try {
      new InternetAddress(mailaddress).validate();
      return true;
    } catch (AddressException e) {
      return false;
    }
  }

  public static boolean userBeenSpammed(Subscriber subscriber) {
    long timeStamp = subscriber.getTimeStamp();
    return timeStamp > (System.currentTimeMillis() - 1800000);
  }

}
