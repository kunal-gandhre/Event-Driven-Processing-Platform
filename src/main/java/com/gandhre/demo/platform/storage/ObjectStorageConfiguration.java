package com.gandhre.demo.platform.storage;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * An S3-compatible client: MinIO locally, or AWS S3 when the endpoint is
 * omitted.
 */
@Configuration
public class ObjectStorageConfiguration {
	@Bean
	S3Client objectStore(@Value("${platform.object-store.endpoint:}") String endpoint,
			@Value("${platform.aws.region:eu-west-1}") String region,
			@Value("${platform.object-store.access-key:}") String accessKey,
			@Value("${platform.object-store.secret-key:}") String secretKey) {
		var builder = S3Client.builder().region(Region.of(region));
		if (!endpoint.isBlank())
			builder.endpointOverride(URI.create(endpoint)).forcePathStyle(true);
		if (!accessKey.isBlank())
			builder.credentialsProvider(
					StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)));
		return builder.build();
	}
}
