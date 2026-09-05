package com.gandhre.demo.platform.event;

import jakarta.validation.constraints.NotBlank;

public class CreateEventRequest {

	@NotBlank
	String eventType;
	@NotBlank
	String aggregateId;
	@NotBlank
	String payload;

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
}
