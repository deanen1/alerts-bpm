package io.github.deanen1.bpm.business.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import io.github.deanen1.bpm.business.domain.Alert;

@Service
public class AlertsService {
  // using a simple in-memory store for now
  // in reality this would like be an HTTP service call
  private Map<String, Alert> db = new HashMap<>();

  public Optional<Alert> get(String id) {
    return Optional.of(db.get(id));
  }

  public void save(Alert alert) {
    db.put(alert.getId(), alert);
  }
}
