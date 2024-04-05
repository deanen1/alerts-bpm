package io.github.deanen1.bpm;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@Deployment(resources = "classpath*:*.bpmn")
@EnableJms
public class AlertsBpmApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlertsBpmApplication.class, args);
  }

}
