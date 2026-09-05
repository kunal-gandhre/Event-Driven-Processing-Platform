package com.gandhre.demo.platform.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventProcessor {
	private static final Logger log = LoggerFactory.getLogger(EventProcessor.class);
	private final ProcessedEventRepository processedEvents;

	public EventProcessor(ProcessedEventRepository processedEvents) {
		this.processedEvents = processedEvents;
	}

	@Transactional
	public void process(ProcessingEvent event) {
		if (processedEvents.existsById(event.getEventId())) {
			log.info("Ignoring duplicate event {}", event.getEventId());
			return;
		}
		// Business processing belongs here. Persisting first provides idempotency
		// across at-least-once deliveries.
		processedEvents.save(new ProcessedEvent(event.getEventId()));
		log.info("Processed {} for aggregate {}", event.getEventType(), event.getAggregateId());
	}
}
