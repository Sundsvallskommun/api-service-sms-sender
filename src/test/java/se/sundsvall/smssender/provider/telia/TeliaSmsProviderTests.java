package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class TeliaSmsProviderTests {

    @Mock
    private TeliaClient mockClient;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private TeliaMapper mockMapper;
    @InjectMocks
    private TeliaSmsProvider provider;

    @Test
    void testSendSms_no_flash_OK() {
        when(mockClient.send(any(SmsServiceRequest.class)))
            .thenReturn(ResponseEntity.noContent().build());

        var isSent = provider.sendSms(createValidSendSmsRequest(), false);
        assertThat(isSent).isTrue();

        verify(mockClient, times(1)).send(any(SmsServiceRequest.class));
    }
}
