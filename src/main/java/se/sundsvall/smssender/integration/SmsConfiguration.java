package se.sundsvall.smssender.integration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(SmsProperties.class)
@Configuration
public class SmsConfiguration {
}
