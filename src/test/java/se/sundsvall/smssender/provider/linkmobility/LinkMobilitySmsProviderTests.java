package se.sundsvall.smssender.provider.linkmobility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.provider.linkmobility.LinkMobilitySmsProvider.PREFIX;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class LinkMobilitySmsProviderTests {

    @Mock
    private LinkMobilitySmsProviderProperties mockProperties;
    @Mock
    private LinkMobilityClient mockClient;

    private LinkMobilitySmsProvider provider;

    @BeforeEach
    void setUp() {
        when(mockProperties.getPlatformId()).thenReturn("platformId");
        when(mockProperties.getPlatformPartnerId()).thenReturn("platformPartnerId");

        provider = new LinkMobilitySmsProvider(mockProperties, mockClient);
    }

    @Test
    void sendSmsWithLinkMobility_shouldReturn_200_OK_and_true() {
        var response = new LinkMobilitySmsResponse();
        response.setStatus(ResponseStatus.SENT);

        when(mockClient.send(any(LinkMobilitySmsRequest.class)))
            .thenReturn(response);

        var isSent = provider.sendSms(validRequest());
        assertThat(isSent).isTrue();

        verify(mockClient, times(1)).send(any(LinkMobilitySmsRequest.class));
    }

    @Test
    void buildLinkMobilitySendSmsRequest_withPlatformIdAndPlatformPartnerId_FromSmsRequest() {
        var request = validRequest();

        var sendSmsRequest = provider.mapFromSmsRequest(request);

        assertThat(sendSmsRequest.getPlatformId()).isEqualTo(mockProperties.getPlatformId());
        assertThat(sendSmsRequest.getPlatformPartnerId()).isEqualTo(mockProperties.getPlatformPartnerId());
        assertThat(sendSmsRequest.getDestination()).isEqualTo(PREFIX + request.getMobileNumber().substring(1));
        assertThat(sendSmsRequest.getSource()).isEqualTo(request.getSender().getName());
        assertThat(sendSmsRequest.getUserData()).isEqualTo(request.getMessage());
    }

    private SendSmsRequest validRequest() {
        return SendSmsRequest.builder()
            .withSender(Sender.builder()
                .withName("sender")
                .build())
            .withMessage("message")
            .withMobileNumber("0701234567")
            .build();
    }
}
