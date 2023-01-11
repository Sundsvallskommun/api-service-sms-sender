package se.sundsvall.smssender.provider.linkmobility;

import java.util.Optional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProvider;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus;

@Service
@EnableConfigurationProperties(LinkMobilitySmsProviderProperties.class)
public class LinkMobilitySmsProvider implements SmsProvider {

    static final String PROVIDER_NAME = "LinkMobility";

    private final LinkMobilitySmsProviderProperties properties;
    private final LinkMobilityClient client;
    private final LinkMobilityMapper mapper;

    LinkMobilitySmsProvider(final LinkMobilitySmsProviderProperties properties,
            final LinkMobilityClient client) {
        this.properties = properties;
        this.client = client;

        mapper = new LinkMobilityMapper(properties);
    }

    @Override
    public boolean sendSms(final SendSmsRequest smsRequest, final boolean flash) {
        verifyFlashCapability(flash);

        var request = mapper.mapFromSendSmsRequest(smsRequest);

        return Optional.ofNullable(client.send(request))
            .map(LinkMobilitySmsResponse::getStatus)
            .map(ResponseStatus::isSent)
            .orElse(false);
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
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public int getPriority() {
        return properties.getPriority();
    }
}
