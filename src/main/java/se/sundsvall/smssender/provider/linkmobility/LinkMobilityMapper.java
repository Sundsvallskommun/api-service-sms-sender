package se.sundsvall.smssender.provider.linkmobility;

import java.util.Map;
import org.springframework.stereotype.Component;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.model.Priority;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;

import static java.util.Optional.ofNullable;

@Component
public class LinkMobilityMapper {

	private final LinkMobilitySmsProviderProperties properties;

	public LinkMobilityMapper(final LinkMobilitySmsProviderProperties properties) {
		this.properties = properties;
	}

	public LinkMobilitySmsRequest toLinkMobilitySmsRequest(final SendSmsRequest smsRequest, final boolean flash) {
		final var request = LinkMobilitySmsRequest.builder()
			.withPlatformId(properties.getPlatformId())
			.withPlatformPartnerId(properties.getPlatformPartnerId())
			.withSource(smsRequest.getSender().getName())
			.withDestinationTON("MSISDN")
			.withDestination(smsRequest.getMobileNumber())
			.withPriority(ofNullable(smsRequest.getPriority())
				.map(Priority::name)
				.orElse(null))
			.withUserData(smsRequest.getMessage())
			.build();

		if (flash) {
			return request.withCustomParameters(Map.of("flash.sms", "true"));
		}

		return request;
	}
}
