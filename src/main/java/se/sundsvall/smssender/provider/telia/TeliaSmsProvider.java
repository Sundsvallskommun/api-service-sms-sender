package se.sundsvall.smssender.provider.telia;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProvider;

import static java.util.Optional.ofNullable;

@Service
@EnableConfigurationProperties(TeliaSmsProviderProperties.class)
public class TeliaSmsProvider implements SmsProvider {

	static final String PROVIDER_NAME = "Telia";

	private final TeliaSmsProviderProperties properties;
	private final TeliaClient client;
	private final TeliaMapper mapper;

	TeliaSmsProvider(final TeliaSmsProviderProperties properties, final TeliaClient client, final TeliaMapper mapper) {
		this.properties = properties;
		this.client = client;
		this.mapper = mapper;
	}

	@Override
	public boolean sendSms(final SendSmsRequest sms, final boolean flash) {
		verifyFlashCapability(flash);

		final var request = mapper.mapFromSendSmsRequest(sms, flash);

		return ofNullable(client.send(request))
			.map(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
			.orElse(false);
	}

	@Override
	public String getName() {
		return PROVIDER_NAME;
	}

	@Override
	public boolean isEnabled() {
		return properties.isEnabled();
	}

	@Override
	public boolean isFlashSmsCapable() {
		return properties.isFlashCapable();
	}

	@Override
	public int getPriority() {
		return properties.getPriority();
	}
}
