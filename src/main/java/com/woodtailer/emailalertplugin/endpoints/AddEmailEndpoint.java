package com.woodtailer.emailalertplugin.endpoints;

import com.woodtailer.emailalertplugin.handler.EndpointHandler;
import com.woodtailer.emailalertplugin.model.MailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class AddEmailEndpoint {

  private final EndpointHandler endpointHandler;

  public AddEmailEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;

  }

  @PutMapping(path = "/addresses/")
  public ResponseEntity<MailResponse> addEmailAddress(@RequestParam String email) {
    return ResponseEntity.ok(endpointHandler.addEmailToSubscribers(email));
  }
}
