package se.sundsvall.smssender.integration.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;
import se.sundsvall.smssender.integration.telia.domain.TeliaResponse;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class TeliaSmsServiceTest {

    @Mock
    private RestTemplate mockRestTemplate;

    private TeliaSmsService service;

    @BeforeEach
    void initMapper() {
        service = new TeliaSmsService(mockRestTemplate);
    }

    @Test
    void sendSmsWithTelia_givenValidSmsRequest_return_200_OK_and_true() {
        var response = new TeliaResponse("0", "ok");

        when(mockRestTemplate.postForObject(any(String.class), any(HttpEntity.class), eq(TeliaResponse.class)))
            .thenReturn(response);

        var isSent = service.sendSms(validRequest());
        assertThat(isSent).isTrue();

        verify(mockRestTemplate, times(1)).postForObject(any(String.class), any(HttpEntity.class), eq(TeliaResponse.class));
    }

    private SendSmsRequest validRequest() {
        return SendSmsRequest.builder()
            .withSender(Sender.builder()
                .withName("sender")
                .build())
            .withMessage("message")
            .withMobileNumber("+46701234567")
            .build();
    }
}
