package se.sundsvall.smssender.integration.linkmobility;

import java.util.Optional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.integration.SmsProvider;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilityResponse;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilitySendSmsRequest;
import se.sundsvall.smssender.integration.linkmobility.domain.ResponseStatus;

@Service
@EnableConfigurationProperties(LinkMobilityProperties.class)
public class LinkMobilitySmsProvider implements SmsProvider<LinkMobilitySendSmsRequest> {

    static final String PROVIDER_NAME = "LinkMobility";

    static final String PREFIX = "+46";

    private final LinkMobilityProperties properties;
    private final LinkMobilityClient client;

    LinkMobilitySmsProvider(final LinkMobilityProperties properties,
            final LinkMobilityClient client) {
        this.properties = properties;
        this.client = client;
    }

    @Override
    public boolean sendSms(final SendSmsRequest smsRequest) {
        var request = mapFromSmsRequest(smsRequest);

        return Optional.ofNullable(client.send(request))
            .map(LinkMobilityResponse::getStatus)
            .map(ResponseStatus::isSent)
            .orElse(false);
    }

    @Override
    public LinkMobilitySendSmsRequest mapFromSmsRequest(final SendSmsRequest smsRequest) {
        return LinkMobilitySendSmsRequest.builder()
            .withPlatformId(properties.getPlatformId())
            .withPlatformPartnerId(properties.getPlatformPartnerId())
            .withDestination(PREFIX + smsRequest.getMobileNumber().substring(1))
            .withSource(smsRequest.getSender().getName())
            .withUserData(smsRequest.getMessage())
            .build();
    }
}
