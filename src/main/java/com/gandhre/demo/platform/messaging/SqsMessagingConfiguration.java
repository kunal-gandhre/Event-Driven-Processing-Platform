package com.gandhre.demo.platform.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gandhre.demo.platform.event.*;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Configuration
@ConditionalOnProperty(name = "platform.messaging.provider", havingValue = "sqs")
class SqsMessagingConfiguration {
	@Bean
	SqsClient sqsClient(@Value("${platform.aws.endpoint:}") String endpoint,
			@Value("${platform.aws.region:eu-west-1}") String region) {
		var builder = SqsClient.builder().region(software.amazon.awssdk.regions.Region.of(region));
		if (!endpoint.isBlank())
			builder.endpointOverride(java.net.URI.create(endpoint));
		return builder.build();
	}

	@Bean
	SnsClient snsClient(@Value("${platform.aws.endpoint:}") String endpoint,
			@Value("${platform.aws.region:eu-west-1}") String region) {
		var builder = SnsClient.builder().region(software.amazon.awssdk.regions.Region.of(region));
		if (!endpoint.isBlank())
			builder.endpointOverride(java.net.URI.create(endpoint));
		return builder.build();
	}
}

@Component
@ConditionalOnProperty(name = "platform.messaging.provider", havingValue = "sqs")
class SqsEventPublisher implements EventPublisher {
	private final SnsClient sns;
	private final ObjectMapper mapper;
	private final String topicArn;

	SqsEventPublisher(SnsClient sns, ObjectMapper mapper, @Value("${platform.sns.topic-arn}") String topicArn) {
		this.sns = sns;
		this.mapper = mapper;
		this.topicArn = topicArn;
	}

	public void publish(ProcessingEvent event) {
		try {
			sns.publish(PublishRequest.builder().topicArn(topicArn).message(mapper.writeValueAsString(event)).build());
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Cannot serialize event", e);
		}
	}
}

@Component
@ConditionalOnProperty(name = "platform.messaging.provider", havingValue = "sqs")
class SqsEventConsumer {
	private final SqsClient sqs;
	private final ObjectMapper mapper;
	private final EventProcessor processor;
	private final String queueUrl;

	SqsEventConsumer(SqsClient sqs, ObjectMapper mapper, EventProcessor processor,
			@Value("${platform.sqs.queue-url}") String queueUrl) {
		this.sqs = sqs;
		this.mapper = mapper;
		this.processor = processor;
		this.queueUrl = queueUrl;
	}

	@Scheduled(fixedDelayString = "${platform.sqs.poll-delay-ms:1000}")
	void consume() throws Exception {
		List<Message> messages = sqs.receiveMessage(
				ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(10).waitTimeSeconds(10).build())
				.messages();
		for (Message message : messages) {
			processor.process(mapper.readValue(message.body(), ProcessingEvent.class));
			sqs.deleteMessage(
					DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build());
		}
	}
}
