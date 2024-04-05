package io.github.deanen1.bpm;

import io.github.deanen1.bpm.jms.MappingJackson2MessageByDestinationConverter;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class AlertBpmApplicationConfiguration {
  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageByDestinationConverter converter =
        new MappingJackson2MessageByDestinationConverter();
    converter.setTargetType(MessageType.TEXT);
    return converter;
  }

  @Bean
  public ArtemisConfigurationCustomizer configurationCustomizers() {
    return config -> {
      try {
        config.addAcceptorConfiguration("netty", "tcp://localhost:61616");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}