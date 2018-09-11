package com.woodtailer.emailalertplugin.endpoints;


import com.woodtailer.emailalertplugin.handler.EndpointHandler;
import com.woodtailer.emailalertplugin.model.WordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class WordEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordEndpoint.class);

  private final EndpointHandler endpointHandler;

  public WordEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }


  @PostMapping(path = "/words/")
  public ResponseEntity<WordResponse> addWord(@RequestParam String email,
      @RequestParam String word) {
    return ResponseEntity.ok(endpointHandler.addWatchWordToSubscriber(email, word));
  }

  @DeleteMapping(path = "/words/")
  public ResponseEntity<WordResponse> removeWord(@RequestParam String email,
      @RequestParam String word) {
    return ResponseEntity.ok(endpointHandler.removeWordFromSubscriber(email, word));
  }
}
