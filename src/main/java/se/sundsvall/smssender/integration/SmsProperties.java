package se.sundsvall.smssender.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "integration.sms")
@ConstructorBinding
@RequiredArgsConstructor
public class SmsProperties {

    private final String provider;

    public Provider getProvider() {
        return Provider.fromValue(provider);
    }
}
