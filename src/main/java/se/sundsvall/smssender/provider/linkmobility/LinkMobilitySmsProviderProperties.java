package se.sundsvall.smssender.provider.linkmobility;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import se.sundsvall.smssender.provider.SmsProviderProperties;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "provider.linkmobility")
public class LinkMobilitySmsProviderProperties extends SmsProviderProperties {

	@NotBlank
	private String platformId;

	@NotBlank
	private String platformPartnerId;

	@Valid
	private BasicAuth basicAuth = new BasicAuth();
}
