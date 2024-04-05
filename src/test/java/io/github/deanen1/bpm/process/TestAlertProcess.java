package io.github.deanen1.bpm.process;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ZeebeSpringTest
@ActiveProfiles({"artemis"})
public class TestAlertProcess {
  @Autowired
  private ZeebeClient zeebe;

  @Test
  void startProcess() throws Exception {
    ProcessVariables vars = new ProcessVariables("123");

    ProcessInstanceEvent processInstance =
        zeebe
            .newCreateInstanceCommand()
            .bpmnProcessId("AlertProcess")
            .latestVersion()
            .variables(vars)
            .send()
            .join();

  }
}