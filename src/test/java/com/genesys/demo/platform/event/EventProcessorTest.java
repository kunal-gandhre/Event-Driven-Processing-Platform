package com.gandhre.demo.platform.event;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.gandhre.demo.platform.event.EventProcessor;
import com.gandhre.demo.platform.event.EventPublisher;
import com.gandhre.demo.platform.event.ProcessedEventRepository;
import com.gandhre.demo.platform.event.ProcessingEvent;

@SpringBootTest
@ActiveProfiles("test")
class EventProcessorTest {
  @Autowired EventProcessor processor;
  @Autowired ProcessedEventRepository repository;
  @MockBean EventPublisher publisher;
  @Test void processesAnEventOnlyOnce() {
    var event = new ProcessingEvent(UUID.randomUUID(), "PAYMENT_RECEIVED", "payment-1", "{}", Instant.now());
    processor.process(event); processor.process(event);
    assertThat(repository.count()).isEqualTo(1);
  }
}
