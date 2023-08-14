package se.sundsvall.smssender.provider.linkmobility;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.model.Priority.NORMAL;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LinkMobilityMapperTests {

    @Mock
    private LinkMobilitySmsProviderProperties mockProperties;
    @InjectMocks
    private LinkMobilityMapper mapper;

    @Test
    void testMapFromSendSmsRequest() {
        final var request = createValidSendSmsRequest();

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, false);

        assertThat(mappedRequest.getPlatformId()).isEqualTo(mockProperties.getPlatformId());
        assertThat(mappedRequest.getPlatformPartnerId()).isEqualTo(mockProperties.getPlatformPartnerId());
        assertThat(mappedRequest.getDestination()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getPriority()).isNull();
        assertThat(mappedRequest.getSource()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getUserData()).isEqualTo(request.getMessage());
        assertThat(mappedRequest.getCustomParameters()).isNull();
    }

    @Test
    void testMapFromSendSmsRequestWithPriority() {
        final var request = createValidSendSmsRequest();
        request.setPriority(NORMAL);

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, false);

        assertThat(mappedRequest.getPriority()).isEqualTo("NORMAL");
    }

    @Test
    void testMapFromSendSmsRequestAsFlashSms() {
        final var request = createValidSendSmsRequest();

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, true);

        assertThat(mappedRequest.getPlatformId()).isEqualTo(mockProperties.getPlatformId());
        assertThat(mappedRequest.getPlatformPartnerId()).isEqualTo(mockProperties.getPlatformPartnerId());
        assertThat(mappedRequest.getDestination()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getSource()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getUserData()).isEqualTo(request.getMessage());
        assertThat(mappedRequest.getCustomParameters()).containsExactly(Map.entry("flash.sms", "true"));
    }
}
