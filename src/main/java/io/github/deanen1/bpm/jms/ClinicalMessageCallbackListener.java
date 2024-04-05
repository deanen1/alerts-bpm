package io.github.deanen1.bpm.jms;

import io.github.deanen1.bpm.business.domain.ClinicalMessageCallback;
import io.github.deanen1.bpm.process.ProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ClinicalMessageCallbackListener {
  @Autowired
  private ProcessManager processManager;

  @JmsListener(id = "clinicalmessagecallback.message.listener", destination = "${clinicalmessagecallback.queue}")
  public void processClinicalMessageCallback(
      @Header("alertId") String alertId,
      @Payload ClinicalMessageCallback callback) {

    processManager.processClinicalMessageCallback(alertId, callback);
  }
}
