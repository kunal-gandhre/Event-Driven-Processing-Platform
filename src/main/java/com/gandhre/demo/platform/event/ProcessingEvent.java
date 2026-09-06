package com.gandhre.demo.platform.event;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessingEvent implements Serializable {

	private static final long serialVersionUID = 138740341675373266L;
	
	UUID eventId;
	String eventType;
	String aggregateId;
	String payload;
	Instant occurredAt;

	public ProcessingEvent(UUID eventId, String eventType, String aggregateId, String payload, Instant occurredAt) {
		super();
		this.eventId = eventId;
		this.eventType = eventType;
		this.aggregateId = aggregateId;
		this.payload = payload;
		this.occurredAt = occurredAt;
	}

	public ProcessingEvent() {
		super();
	}

	public UUID getEventId() {
		return eventId;
	}

	public void setEventId(UUID eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Instant getOccurredAt() {
		return occurredAt;
	}

	public void setOccurredAt(Instant occurredAt) {
		this.occurredAt = occurredAt;
	}
}
