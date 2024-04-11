package io.github.deanen1.bpm.integration.http;

import io.github.deanen1.bpm.integration.IntegrationConstants;
import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.domain.AlertEvent;
import io.github.deanen1.bpm.business.domain.ClinicalMessageCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;

@Configuration
@EnableIntegration
public class HttpIntegration {
  @Bean
  public IntegrationFlow newAlertHttpInboundGateway() {
    return IntegrationFlows
        .from(
            Http.inboundGateway("/alert")
                .autoStartup(true)
                .requestMapping(m -> {
                  m.methods(HttpMethod.POST);
                })
                .requestPayloadType(Alert.class)
                .replyChannel(IntegrationConstants.NEW_ALERT_RESPONSE_CHANNEL)
                .requestTimeout(10000)
                .replyTimeout(10000)
        )
        .channel(IntegrationConstants.NEW_ALERT_CHANNEL)
        .get();
  }

  @Bean
  public IntegrationFlow newAlertEventHttpInboundGateway() {
    return IntegrationFlows
        .from(
            Http.inboundChannelAdapter("/alert/{id}/event")
                .autoStartup(true)
                .requestMapping(m -> {
                  m.methods(HttpMethod.POST);
                })
                .requestPayloadType(AlertEvent.class)
                .headerExpression(IntegrationConstants.ALERTID_MSG_HEADER, "#pathVariables.id")
                .requestTimeout(10000)
        )
        .channel(IntegrationConstants.NEW_ALERT_EVENT_CHANNEL)
        .get();
  }

  @Bean
  public IntegrationFlow newClinicalMessageCallbackHttpInboundGateway() {
    return IntegrationFlows
        .from(
            Http.inboundChannelAdapter("/alert/{id}/callback")
                .autoStartup(true)
                .requestMapping(m -> {
                  m.methods(HttpMethod.POST);
                })
                .requestPayloadType(ClinicalMessageCallback.class)
                .headerExpression(IntegrationConstants.ALERTID_MSG_HEADER, "#pathVariables.id")
                .requestTimeout(10000)
        )
        .channel(IntegrationConstants.NEW_CLINICAL_MESSAGE_CALLBACK_CHANNEL)
        .get();
  }
}
