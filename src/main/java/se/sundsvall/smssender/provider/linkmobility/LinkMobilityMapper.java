package se.sundsvall.smssender.provider.linkmobility;

import java.util.Map;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;

class LinkMobilityMapper {

    private final LinkMobilitySmsProviderProperties properties;

    LinkMobilityMapper(final LinkMobilitySmsProviderProperties properties) {
        this.properties = properties;
    }

    LinkMobilitySmsRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest, final boolean flash) {
        var request =  LinkMobilitySmsRequest.builder()
            .withPlatformId(properties.getPlatformId())
            .withPlatformPartnerId(properties.getPlatformPartnerId())
            .withDestinationTON("MSISDN")
            .withDestination(smsRequest.getMobileNumber())
            .withSource(smsRequest.getSender().getName())
            .withUserData(smsRequest.getMessage())
            .build();

        if (flash) {
            return request.withCustomParameters(Map.of("flash.sms", "true"));
        }

        return request;
    }
}
