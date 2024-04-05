package io.github.deanen1.bpm.jms;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.deanen1.bpm.business.domain.Alert;
import io.github.deanen1.bpm.business.domain.AlertEvent;
import io.github.deanen1.bpm.business.domain.ClinicalMessageCallback;
import javax.jms.JMSException;
import javax.jms.Message;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

public class MappingJackson2MessageByDestinationConverter extends MappingJackson2MessageConverter {
  @Override
  protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
    String destinationName = message.getJMSDestination().toString();
    if (destinationName.contains("Alert.json")) {
      return TypeFactory.defaultInstance().constructType(Alert.class);
    } else if (destinationName.contains("ClinicalMessageCallback.json")){
      return TypeFactory.defaultInstance().constructType(ClinicalMessageCallback.class);
    } else if (destinationName.contains("AlertEvent.json")) {
      return TypeFactory.defaultInstance().constructType(AlertEvent.class);
    } else {
      return super.getJavaTypeForMessage(message);
    }
  }
}
