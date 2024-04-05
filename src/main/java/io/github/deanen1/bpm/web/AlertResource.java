package io.github.deanen1.bpm.web;

import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.domain.AlertEvent;
import io.github.deanen1.bpm.business.domain.ClinicalMessageCallback;
import io.github.deanen1.bpm.business.service.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.github.deanen1.bpm.process.ProcessManager;

@RestController
public class AlertResource {
  @Autowired
  private ProcessManager processManager;

  @PostMapping("/alert")
  public ResponseEntity<String> postAlert(@RequestBody Alert alert) {

    processManager.startProcess(alert);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/alert/{id}/event")
  public ResponseEntity<String> postAlertEvent(@PathVariable("id") String alertId, @RequestBody AlertEvent event) {

    processManager.processEvent(alertId, event);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/alert/{id}/callback")
  public ResponseEntity<String> postClinicalMessageCallbacl(
      @PathVariable("id") String alertId, @RequestBody ClinicalMessageCallback callback) {

    processManager.publishMessageForClinicalMessageCallback(alertId, callback);

    return ResponseEntity.ok().build();
  }
}