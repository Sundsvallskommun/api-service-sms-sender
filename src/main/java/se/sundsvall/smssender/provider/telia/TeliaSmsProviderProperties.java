package se.sundsvall.smssender.provider.telia;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import se.sundsvall.smssender.provider.SmsProviderProperties;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "provider.telia")
public class TeliaSmsProviderProperties extends SmsProviderProperties {

	@Valid
	private OAuth2 oauth2 = new OAuth2();
}
