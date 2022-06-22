package se.sundsvall.smssender.integration.linkmobility;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.integration.SmsService;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilityResponse;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilitySendSmsRequest;
import se.sundsvall.smssender.integration.linkmobility.domain.ResponseStatus;

@Service
public class LinkMobilityService implements SmsService<LinkMobilitySendSmsRequest> {

    static final String PREFIX = "+46";

    private final LinkMobilityProperties properties;
    private final RestTemplate restTemplate;

    public LinkMobilityService(
            LinkMobilityProperties properties,
            @Qualifier("integration.linkmobility.resttemplate") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendSms(SendSmsRequest smsRequest) {
        var request = mapFromSmsRequest(smsRequest);

        return Optional.ofNullable(restTemplate.postForObject("/sms/send", new HttpEntity<>(request, createHeaders()), LinkMobilityResponse.class))
                .map(LinkMobilityResponse::getStatus)
                .map(ResponseStatus::isSent)
                .orElse(false);
    }

    @Override
    public LinkMobilitySendSmsRequest mapFromSmsRequest(SendSmsRequest smsRequest) {
        return LinkMobilitySendSmsRequest.builder()
                .withPlatformId(properties.getPlatformId())
                .withPlatformPartnerId(properties.getPlatformPartnerId())
                .withDestination("+46" + smsRequest.getMobileNumber().substring(1))
                .withSource(smsRequest.getSender().getName())
                .withUserData(smsRequest.getMessage())
                .build();
    }
}
