package se.sundsvall.smssender.provider.telia;

import java.util.Optional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProvider;
import se.sundsvall.smssender.provider.telia.domain.TeliaSmsRequest;
import se.sundsvall.smssender.provider.telia.domain.TeliaSmsResponse;

@Service
@EnableConfigurationProperties(TeliaSmsProviderProperties.class)
public class TeliaSmsProvider implements SmsProvider<TeliaSmsRequest> {

    static final String PROVIDER_NAME = "Telia";

    private final TeliaClient client;

    TeliaSmsProvider(final TeliaClient client) {
        this.client = client;
    }

    @Override
    public boolean sendSms(final SendSmsRequest sms) {
        var request = mapFromSmsRequest(sms);

        return Optional.ofNullable(client.send(request))
            .map(TeliaSmsResponse::isSent)
            .orElse(false);
    }

    @Override
    public TeliaSmsRequest mapFromSmsRequest(final SendSmsRequest smsRequest) {
        return TeliaSmsRequest.builder()
            //.withOriginator(smsRequest.getSender().getName())
            .withMessage(smsRequest.getMessage())
            .withDestinationNumber(smsRequest.getMobileNumber())
            .build();
    }
}

