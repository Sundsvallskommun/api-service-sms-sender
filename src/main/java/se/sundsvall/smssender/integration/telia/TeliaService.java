package se.sundsvall.smssender.integration.telia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.integration.SmsService;
import se.sundsvall.smssender.integration.telia.domain.TeliaResponse;
import se.sundsvall.smssender.integration.telia.domain.TeliaSendSmsRequest;

@Service
public class TeliaService implements SmsService<TeliaSendSmsRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(TeliaService.class);

    private final RestTemplate restTemplate;

    public TeliaService(@Qualifier("integration.telia.resttemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendSms(SendSmsRequest sms) {
        var request = mapFromSmsRequest(sms);
        LOG.debug(request.toString());

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

