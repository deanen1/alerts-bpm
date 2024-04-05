package io.github.deanen1.bpm.business.service;

import io.github.deanen1.bpm.business.domain.ClinicalMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClinicalMessagingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClinicalMessagingService.class);

  public void sendClinicalMessage(ClinicalMessage message) {
    LOGGER.info("Sending clinical message to {} stating {}",
        message.getAddressee(), message.getMessage());
  }
}
