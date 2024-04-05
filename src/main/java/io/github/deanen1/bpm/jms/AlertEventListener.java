package io.github.deanen1.bpm.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import io.github.deanen1.bpm.business.domain.AlertEvent;
import io.github.deanen1.bpm.process.ProcessManager;

@Component
public class AlertEventListener {
  @Autowired
  private ProcessManager processManager;

  @JmsListener(id = "alert.event.listener", destination = "${alertevent.queue}")
  public void processAlert(
      @Header("alertId") String alertId,
      @Payload AlertEvent event) {

    processManager.processEvent(alertId, event);
  }
}
