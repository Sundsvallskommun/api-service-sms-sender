package se.sundsvall.smssender.integration.telia;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.integration.Provider;
import se.sundsvall.smssender.integration.SmsService;
import se.sundsvall.smssender.integration.support.annotation.ConditionalOnConfiguredProvider;
import se.sundsvall.smssender.integration.telia.domain.TeliaResponse;
import se.sundsvall.smssender.integration.telia.domain.TeliaSendSmsRequest;

@Service
@ConditionalOnConfiguredProvider(Provider.TELIA)
public class TeliaSmsService implements SmsService<TeliaSendSmsRequest> {

    private final RestTemplate restTemplate;

    public TeliaSmsService(@Qualifier("integration.telia.resttemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendSms(SendSmsRequest sms) {
        var request = mapFromSmsRequest(sms);

        var response = restTemplate.postForObject("/sendSms", new HttpEntity<>(request, createHeaders()), TeliaResponse.class);

        if (response == null) {
            throw Problem.valueOf(Status.BAD_GATEWAY, "No response from Telia");
        }

        return response.getStatusCode().equals("0") && response.getStatusMessage().equals("ok");
    }

    @Override
    public TeliaSendSmsRequest mapFromSmsRequest(SendSmsRequest smsRequest) {
        return TeliaSendSmsRequest.builder()
            //.withOriginator(smsRequest.getSender().getName())
            .withMessage(smsRequest.getMessage())
            .withDestinationNumber(smsRequest.getMobileNumber())
            .build();
    }
}

