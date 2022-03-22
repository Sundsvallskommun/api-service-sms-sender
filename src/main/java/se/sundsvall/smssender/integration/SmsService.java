package se.sundsvall.smssender.integration;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import se.sundsvall.smssender.api.domain.SmsRequest;

public interface SmsService<REQUEST> {

    boolean sendSms(SmsRequest request);

    REQUEST mapFromSmsRequest(SmsRequest smsRequest);

    default HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
