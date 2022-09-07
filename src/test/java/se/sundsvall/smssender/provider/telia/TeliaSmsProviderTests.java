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
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;
import se.sundsvall.smssender.provider.telia.domain.TeliaSmsRequest;
import se.sundsvall.smssender.provider.telia.domain.TeliaSmsResponse;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class TeliaSmsProviderTests {

    @Mock
    private TeliaClient mockClient;

    @InjectMocks
    private TeliaSmsProvider provider;

    @Test
    void sendSmsWithTelia_givenValidSmsRequest_return_200_OK_and_true() {
        var response = new TeliaSmsResponse("0", "ok");

        when(mockClient.send(any(TeliaSmsRequest.class)))
            .thenReturn(response);

        var isSent = provider.sendSms(validRequest());
        assertThat(isSent).isTrue();

        verify(mockClient, times(1)).send(any(TeliaSmsRequest.class));
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
