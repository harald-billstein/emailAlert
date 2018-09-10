package com.woodtailer.emailalertplugin;

import com.woodtailer.emailalertplugin.controllers.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EmailAlertPlugin {

  private MainController mainController;

  public EmailAlertPlugin(MainController mainController) {
    this.mainController = mainController;
  }

  public static void main(String[] args) {
    SpringApplication.run(EmailAlertPlugin.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void doAfterStartup() {
    mainController.start();
  }
}
