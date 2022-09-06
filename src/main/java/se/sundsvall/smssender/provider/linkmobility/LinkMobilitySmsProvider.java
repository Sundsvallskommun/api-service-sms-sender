package se.sundsvall.smssender.provider.linkmobility;

import java.util.Optional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProvider;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus;

@Service
@EnableConfigurationProperties(LinkMobilitySmsProviderProperties.class)
public class LinkMobilitySmsProvider implements SmsProvider<LinkMobilitySmsRequest> {

    static final String PROVIDER_NAME = "LinkMobility";

    static final String PREFIX = "+46";

    private final LinkMobilitySmsProviderProperties properties;
    private final LinkMobilityClient client;

    LinkMobilitySmsProvider(final LinkMobilitySmsProviderProperties properties,
            final LinkMobilityClient client) {
        this.properties = properties;
        this.client = client;
    }

    @Override
    public boolean sendSms(final SendSmsRequest smsRequest) {
        var request = mapFromSmsRequest(smsRequest);

        return Optional.ofNullable(client.send(request))
            .map(LinkMobilitySmsResponse::getStatus)
            .map(ResponseStatus::isSent)
            .orElse(false);
    }

    @Override
    public LinkMobilitySmsRequest mapFromSmsRequest(final SendSmsRequest smsRequest) {
        return LinkMobilitySmsRequest.builder()
            .withPlatformId(properties.getPlatformId())
            .withPlatformPartnerId(properties.getPlatformPartnerId())
            .withDestination(PREFIX + smsRequest.getMobileNumber().substring(1))
            .withSource(smsRequest.getSender().getName())
            .withUserData(smsRequest.getMessage())
            .build();
    }
}
