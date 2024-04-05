package io.github.deanen1.bpm.business.domain;

public class AlertEvent {
  public enum AlertEventType {
    ACK, REJECT
  }

  private String id;
  private AlertEventType type;
  private String user;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AlertEventType getType() {
    return type;
  }

  public void setType(AlertEventType type) {
    this.type = type;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
