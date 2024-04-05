package io.github.deanen1.bpm.business.domain;

import java.util.ArrayList;
import java.util.List;

public class Alert {
  private String id;
  private String text;

  private List<AlertEvent> events = new ArrayList<>();
  private List<ClinicalMessageCallback> clinicalMessageCallbacks = new ArrayList<>();

  public Alert() {
  }

  public Alert(String text) {
    this.text = text;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<AlertEvent> getEvents() {
    return events;
  }

  public void setEvents(List<AlertEvent> events) {
    this.events = events;
  }

  public void addEvent(AlertEvent event) {
    events.add(event);
  }

  public List<ClinicalMessageCallback> getClinicalMessageCallbacks() {
    return clinicalMessageCallbacks;
  }

  public void setClinicalMessageCallbacks(
      List<ClinicalMessageCallback> clinicalMessageCallbacks) {
    this.clinicalMessageCallbacks = clinicalMessageCallbacks;
  }

  public void addClinicalMessageCallback(ClinicalMessageCallback callback) {
    clinicalMessageCallbacks.add(callback);
  }
}
