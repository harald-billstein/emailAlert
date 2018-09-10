package com.woodtailer.emailalertplugin.endpoints;


import com.woodtailer.emailalertplugin.model.MailResponse;
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


  @PostMapping(path = "/words/")
  public ResponseEntity<MailResponse> addWord(@RequestParam String email,
      @RequestParam String word) {
    LOGGER.info("ADD : " + email + " : " + word);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping(path = "/words/")
  public ResponseEntity<MailResponse> removeWord(@RequestParam String email,
      @RequestParam String word) {
    LOGGER.info("REMOVE : " + email + " : " + word);
    return ResponseEntity.ok(null);
  }
}
