package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class TeliaSmsProviderTests {

    @Mock
    private TeliaClient mockClient;

    @InjectMocks
    private TeliaSmsProvider provider;

    @Test
    void sendSmsWithTelia_givenValidSmsRequest_return_200_OK_and_true() {
        when(mockClient.send(any(SmsServiceRequest.class)))
            .thenReturn(ResponseEntity.noContent().build());

        var isSent = provider.sendSms(validRequest());
        assertThat(isSent).isTrue();

        verify(mockClient, times(1)).send(any(SmsServiceRequest.class));
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
