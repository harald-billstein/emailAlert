package com.woodtailer.emailalertplugin.endpoints;

import com.woodtailer.emailalertplugin.handler.EndpointHandler;
import com.woodtailer.emailalertplugin.model.IncommingRequest;
import com.woodtailer.emailalertplugin.model.MailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class EmailEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailEndpoint.class);

  private final EndpointHandler endpointHandler;

  public EmailEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;

  }

  @PutMapping(path = "/addresses/")
  public ResponseEntity<MailResponse> addEmailAddress(@RequestBody IncommingRequest request) {
    return ResponseEntity.ok(endpointHandler.addEmailToSubscribers(request));
  }

  @DeleteMapping(path = "/addresses/")
  public ResponseEntity<MailResponse> addEmailAddress(@RequestParam String email) {
    LOGGER.info("EMAIL TO BE REMOVED : " + email);
    return ResponseEntity.ok(endpointHandler.removeEmailAddress(email));
  }

}
