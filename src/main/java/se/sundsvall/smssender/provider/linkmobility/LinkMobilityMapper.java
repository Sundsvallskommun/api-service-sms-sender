package se.sundsvall.smssender.provider.linkmobility;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;

class LinkMobilityMapper {

    static final String PREFIX = "+46";

    private final LinkMobilitySmsProviderProperties properties;

    LinkMobilityMapper(final LinkMobilitySmsProviderProperties properties) {
        this.properties = properties;
    }

    LinkMobilitySmsRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest) {
        return LinkMobilitySmsRequest.builder()
            .withPlatformId(properties.getPlatformId())
            .withPlatformPartnerId(properties.getPlatformPartnerId())
            .withDestination(PREFIX + smsRequest.getMobileNumber().substring(1))
            .withSource(smsRequest.getSender().getName())
            .withUserData(smsRequest.getMessage())
            .build();
    }
}
