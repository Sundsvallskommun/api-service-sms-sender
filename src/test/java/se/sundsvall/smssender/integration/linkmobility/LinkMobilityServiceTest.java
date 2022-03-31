package se.sundsvall.smssender.integration.linkmobility;

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
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilityResponse;
import se.sundsvall.smssender.integration.linkmobility.domain.ResponseStatus;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class LinkMobilityServiceTest {

    @Mock
    private LinkMobilityProperties mockProperties;
    @Mock
    private RestTemplate mockRestTemplate;

    private LinkMobilityService service;

    @BeforeEach
    void initMapper() {
        when(mockProperties.getPlatformId()).thenReturn("platformId");
        when(mockProperties.getPlatformPartnerId()).thenReturn("platformPartnerId");

        service = new LinkMobilityService(mockProperties, mockRestTemplate);
    }

    @Test
    void sendSmsWithLinkMobility_shouldReturn_200_OK_and_true() {
        var response = new LinkMobilityResponse();
        response.setStatus(ResponseStatus.SENT);

        when(mockRestTemplate.postForObject(any(String.class), any(HttpEntity.class), eq(LinkMobilityResponse.class)))
            .thenReturn(response);

        var isSent = service.sendSms(validRequest());
        assertThat(isSent).isTrue();

        verify(mockRestTemplate, times(1)).postForObject(any(String.class), any(HttpEntity.class), eq(LinkMobilityResponse.class));
    }

    @Test
    void buildLinkMobilitySendSmsRequest_withPlatformIdAndPlatformPartnerId_FromSmsRequest() {
        var request = validRequest();

        var sendSmsRequest = service.mapFromSmsRequest(request);

        assertThat(sendSmsRequest.getPlatformId()).isEqualTo(mockProperties.getPlatformId());
        assertThat(sendSmsRequest.getPlatformPartnerId()).isEqualTo(mockProperties.getPlatformPartnerId());
        assertThat(sendSmsRequest.getDestination()).isEqualTo(request.getMobileNumber());
        assertThat(sendSmsRequest.getSource()).isEqualTo(request.getSender().getName());
        assertThat(sendSmsRequest.getUserData()).isEqualTo(request.getMessage());
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
