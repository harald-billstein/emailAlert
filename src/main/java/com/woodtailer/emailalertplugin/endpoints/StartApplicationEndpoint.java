package com.woodtailer.emailalertplugin.endpoints;

import com.woodtailer.emailalertplugin.handler.EndpointHandler;
import com.woodtailer.emailalertplugin.model.StartApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/v1")
public class StartApplicationEndpoint {


  private final EndpointHandler endpointHandler;

  public StartApplicationEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;

  }

  @PostMapping(path = "/emailalert/start/")
  public ResponseEntity<StartApplicationResponse> addEmailAddress() {
    StartApplicationResponse startApplicationResponse = new StartApplicationResponse();
    startApplicationResponse.setSuccess(true);
    endpointHandler.startApplication();
    return ResponseEntity.ok(startApplicationResponse);
  }


}
