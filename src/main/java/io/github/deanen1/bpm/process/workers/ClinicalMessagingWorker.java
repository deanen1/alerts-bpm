package io.github.deanen1.bpm.process.workers;

import java.util.Map;
import io.camunda.zeebe.spring.client.annotation.CustomHeaders;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.domain.ClinicalMessage;
import io.github.deanen1.bpm.business.service.AlertsService;
import io.github.deanen1.bpm.business.service.ClinicalMessagingService;
import io.github.deanen1.bpm.process.AlertNotFoundException;
import io.github.deanen1.bpm.process.ProcessConstants;
import io.github.deanen1.bpm.process.ProcessVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClinicalMessagingWorker {
  @Autowired
  private ClinicalMessagingService clinicalMessagingService;

  @Autowired
  private AlertsService alertService;

  @JobWorker(type = "send-clinical-message")
  public void notifyCareTeam(
      @Variable(name = "id") String id,
      @CustomHeaders Map<String, String> headers,
      @VariablesAsType ProcessVariables variables) {

    Alert alert = alertService.get(id).orElseThrow(AlertNotFoundException::new);
    String recipient = headers.get(ProcessConstants.HEADER_RECIPIENT);

    ClinicalMessage message =
        new ClinicalMessage(id, recipient, alert.getText());

    clinicalMessagingService.sendClinicalMessage(message);
  }
}