package se.sundsvall.smssender.provider.telia;

import org.springframework.boot.context.properties.ConfigurationProperties;

import se.sundsvall.smssender.provider.SmsProviderProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "provider.telia")
public class TeliaSmsProviderProperties extends SmsProviderProperties {

    private OAuth2 oauth2 = new OAuth2();

}
