package io.github.deanen1.bpm.integration.jms;

import java.util.Map;
import io.github.deanen1.bpm.integration.IntegrationConstants;
import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Configuration
@EnableIntegration
public class JmsIntegration {

  @Value("${alert.queue}")
  private String alertQueue;

  @Value("${alertevent.queue}")
  private String alertEventQueue;

  @Value("${clinicalmessagecallback.queue}")
  private String clinicalMessageCallbackQueue;

  @Bean
  public IntegrationFlow newAlertJmsChannelAdapter(
      ConnectionFactory cf,
      MappingJackson2MessageByDestinationConverter converter) {

    return IntegrationFlows
        .from(Jms
            .messageDrivenChannelAdapter(cf)
            .destination(alertQueue)
            .jmsMessageConverter(converter)
        )
        .enrichHeaders(Map.of("replyChannel", "nullChannel"))
        .channel(IntegrationConstants.NEW_ALERT_CHANNEL)
        .nullChannel();
  }

  @Bean
  public IntegrationFlow newAlertEventJmsChannelAdapter(
      ConnectionFactory cf,
      MappingJackson2MessageByDestinationConverter converter) {

    return IntegrationFlows
        .from(Jms
            .messageDrivenChannelAdapter(cf)
            .destination(alertEventQueue)
            .jmsMessageConverter(converter)
        )
        .channel(IntegrationConstants.NEW_ALERT_EVENT_CHANNEL)
        .nullChannel();
  }

  @Bean
  public IntegrationFlow newClinicalMessageCallbackJmsChannelAdapter(
      ConnectionFactory cf,
      MappingJackson2MessageByDestinationConverter converter) {

    return IntegrationFlows
        .from(Jms
            .messageDrivenChannelAdapter(cf)
            .destination(clinicalMessageCallbackQueue)
            .jmsMessageConverter(converter)
        )
        .channel(IntegrationConstants.NEW_CLINICAL_MESSAGE_CALLBACK_CHANNEL)
        .nullChannel();
  }
}
