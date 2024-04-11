package io.github.deanen1.bpm.process;

import java.util.Optional;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.github.deanen1.bpm.integration.IntegrationConstants;
import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.domain.AlertEvent;
import io.github.deanen1.bpm.business.domain.AlertEvent.AlertEventType;
import io.github.deanen1.bpm.business.domain.ClinicalMessageCallback;
import io.github.deanen1.bpm.business.service.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProcessManager {
  @Autowired
  private ZeebeClient zeebeClient;

  @Autowired
  private AlertsService alertsService;

  @ServiceActivator(
      inputChannel = IntegrationConstants.NEW_ALERT_CHANNEL)
  public String startProcess(Alert alert) {
    ProcessVariables vars = new ProcessVariables(alert.getId());

    ProcessInstanceEvent processInstance =
        zeebeClient
            .newCreateInstanceCommand()
            .bpmnProcessId("AlertProcess")
            .latestVersion()
            .variables(vars)
            .send()
            .join();

    alertsService.save(alert);
    return String.valueOf(processInstance.getProcessInstanceKey());
  }

  @ServiceActivator(inputChannel = IntegrationConstants.NEW_ALERT_EVENT_CHANNEL, requiresReply = "false")
  public void processEvent(
      @Header(IntegrationConstants.ALERTID_MSG_HEADER) String alertId,
      @Payload AlertEvent event) {

    Optional<Alert> alert = alertsService.get(alertId);

    if (alert.isPresent()) {
      alert.get().addEvent(event);
      if (event.getType() == AlertEventType.ACK) {
        publishMessage(
            alertId, ProcessConstants.MESSAGE_PROVIDER_ACKNOWLEDGEMENT);
      }
    } else {
      throw new AlertNotFoundException("No alert found with id " + alertId);
    }
  }

  @ServiceActivator(inputChannel = IntegrationConstants.NEW_CLINICAL_MESSAGE_CALLBACK_CHANNEL, requiresReply = "false")
  public void processClinicalMessageCallback(
      @Header(IntegrationConstants.ALERTID_MSG_HEADER) String alertId,
      @Payload ClinicalMessageCallback callback) {

    Optional<Alert> alert = alertsService.get(alertId);

    if (alert.isPresent()) {
      alert.get().addClinicalMessageCallback(callback);

      switch (callback.getStatusCode()) {
        case 10:
          publishMessage(
              alertId, ProcessConstants.MESSAGE_CARETEAM_NOTIFIED);
          break;
        case 50:
        case 100:
        case 120:
          publishMessage(
              alertId,
              ProcessConstants.MESSAGE_CARETEAM_NOTIFIFICATION_FAILED);
          break;
        default:
          break;
      }
    } else {
      throw new AlertNotFoundException("No alert found with id " + alertId);
    }
  }

  private void publishMessage(String correlationKey, String messageName) {
    zeebeClient
        .newPublishMessageCommand()
        .messageName(messageName)
        .correlationKey(correlationKey)
        .send();
  }
}
