package com.woodtailer.emailalertplugin.handler;

import com.woodtailer.emailalertplugin.controllers.MainController;
import com.woodtailer.emailalertplugin.mailservice.EmailSubscribers;
import com.woodtailer.emailalertplugin.model.MailResponse;
import com.woodtailer.emailalertplugin.utility.EmailVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private final EmailSubscribers emailSubscribers;
  private final MainController mainController;


  public EndpointHandler(EmailSubscribers emailSubscribers, MainController mainController) {
    this.emailSubscribers = emailSubscribers;
    this.mainController = mainController;

  }

  public MailResponse addEmailToSubscribers(String email) {


    MailResponse mailResponse = new MailResponse();

    if (EmailVerifier.validateMailaddress(email) && !EmailVerifier
        .isDuplicate(email, emailSubscribers)) {
      emailSubscribers.getAddresses().add(email.toLowerCase());
      mailResponse.setSuccess(true);
      mailResponse.setMessage("Mail to subscribers list");
      mailResponse.setRegisteredMail(email);
      LOGGER.info("ADDING MAIL TO SUBSCRIBERS");
    } else {
      mailResponse.setSuccess(false);
      mailResponse.setMessage("Duplicate or not valid email");
      mailResponse.setRegisteredMail(email);
    }
    return mailResponse;
  }

  public void startApplication() {
    mainController.startApplication();
  }
}
