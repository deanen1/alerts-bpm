package io.github.deanen1.bpm.jms;

import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.service.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import io.github.deanen1.bpm.process.ProcessManager;

@Component
public class AlertMessageListener {
  @Autowired
  private ProcessManager processManager;

  @JmsListener(id = "alert.message.listener", destination = "${alert.queue}")
  public void processAlert(@Payload Alert alert) {
    processManager.startProcess(alert);
  }
}
