package se.sundsvall.smssender.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
class RetryConfiguration {

	@Bean
	RetryTemplate retryTemplate() {
		var retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(new NoBackOffPolicy());
		retryTemplate.setRetryPolicy(new NeverRetryPolicy());
		return retryTemplate;
	}
}
