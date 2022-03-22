package se.sundsvall.smssender.integration.linkmobility;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import se.sundsvall.smssender.api.domain.SmsRequest;
import se.sundsvall.smssender.integration.SmsService;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilityResponse;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilitySendSmsRequest;
import se.sundsvall.smssender.integration.linkmobility.domain.ResponseStatus;

@Service
public class LinkMobilityService implements SmsService<LinkMobilitySendSmsRequest> {

    private final LinkMobilityProperties properties;
    private final RestTemplate restTemplate;

    public LinkMobilityService(
            LinkMobilityProperties properties,
            @Qualifier("integration.linkmobility.resttemplate") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendSms(SmsRequest smsRequest) {
        var request = mapFromSmsRequest(smsRequest);

        return Optional.ofNullable(restTemplate.postForObject("/sms/send", new HttpEntity<>(request, createHeaders()), LinkMobilityResponse.class))
                .map(LinkMobilityResponse::getStatus)
                .map(ResponseStatus::isSent)
                .orElse(false);
    }

    @Override
    public LinkMobilitySendSmsRequest mapFromSmsRequest(SmsRequest smsRequest) {
        return LinkMobilitySendSmsRequest.builder()
                .withPlatformId(properties.getPlatformId())
                .withPlatformPartnerId(properties.getPlatformPartnerId())
                .withDestination(smsRequest.getMobileNumber())
                .withSource(smsRequest.getSender())
                .withUserData(smsRequest.getMessage())
                .build();
    }
}
