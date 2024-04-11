package io.github.deanen1.bpm.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class IntegrationChannels {
  @Bean(name = IntegrationConstants.NEW_ALERT_CHANNEL)
  public MessageChannel newAlertChannel() {
    return MessageChannels.publishSubscribe().get();
  }

  @Bean(name = IntegrationConstants.NEW_ALERT_RESPONSE_CHANNEL)
  public MessageChannel newAlertResponseChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(name = IntegrationConstants.NEW_ALERT_EVENT_CHANNEL)
  public MessageChannel newAlertEventChannel() {
    return MessageChannels.publishSubscribe().get();
  }

  @Bean(name = IntegrationConstants.NEW_CLINICAL_MESSAGE_CALLBACK_CHANNEL)
  public MessageChannel newClinicalMessageCallbackChannel() {
    return MessageChannels.publishSubscribe().get();
  }
}