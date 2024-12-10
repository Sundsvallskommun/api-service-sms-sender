package se.sundsvall.smssender.provider.linkmobility;

import static java.util.Optional.ofNullable;

import java.util.Map;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.model.Priority;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;

class LinkMobilityMapper {

	private final LinkMobilitySmsProviderProperties properties;

	LinkMobilityMapper(final LinkMobilitySmsProviderProperties properties) {
		this.properties = properties;
	}

	LinkMobilitySmsRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest, final boolean flash) {
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
