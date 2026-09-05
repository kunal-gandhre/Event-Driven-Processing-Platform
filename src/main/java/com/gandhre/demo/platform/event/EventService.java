package com.gandhre.demo.platform.event;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EventService {
	private final EventPublisher publisher;

	public EventService(EventPublisher publisher) {
		this.publisher = publisher;
	}

	public ProcessingEvent create(CreateEventRequest request) {
		ProcessingEvent event = new ProcessingEvent(UUID.randomUUID(), request.getEventType(), request.getAggregateId(),
				request.getPayload(), Instant.now());
		publisher.publish(event);
		return event;
	}
}
