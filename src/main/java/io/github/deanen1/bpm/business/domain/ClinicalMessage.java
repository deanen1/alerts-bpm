package io.github.deanen1.bpm.business.domain;

public class ClinicalMessage {
  private String clientMessageId;
  private String addressee;
  private String message;

  public ClinicalMessage() {
  }

  public ClinicalMessage(String clientMessageId, String addressee, String message) {
    this.clientMessageId = clientMessageId;
    this.addressee = addressee;
    this.message = message;
  }

  public String getClientMessageId() {
    return clientMessageId;
  }

  public void setClientMessageId(String clientMessageId) {
    this.clientMessageId = clientMessageId;
  }

  public String getAddressee() {
    return addressee;
  }

  public void setAddressee(String addressee) {
    this.addressee = addressee;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
