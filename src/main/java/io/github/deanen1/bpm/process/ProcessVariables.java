package io.github.deanen1.bpm.process;

public class ProcessVariables {
  private String id;

  public ProcessVariables() {
  }

  public ProcessVariables(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}