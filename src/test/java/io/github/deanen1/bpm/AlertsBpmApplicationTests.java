package io.github.deanen1.bpm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"h2", "artemis"})
class AlertsBpmApplicationTests {

  @Test
  void contextLoads() {
  }

}
