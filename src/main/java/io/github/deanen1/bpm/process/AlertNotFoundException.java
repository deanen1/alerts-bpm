package io.github.deanen1.bpm.process;

public class AlertNotFoundException extends RuntimeException {

  public AlertNotFoundException() {
  }

  public AlertNotFoundException(String message) {
    super(message);
  }
}
