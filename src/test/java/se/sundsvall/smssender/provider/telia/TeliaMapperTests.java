package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.model.Priority.HIGH;
import static se.sundsvall.smssender.model.Priority.NORMAL;

import org.junit.jupiter.api.Test;

class TeliaMapperTests {

    private final TeliaMapper mapper = new TeliaMapper();

    @Test
    void testMapFromSendSmsRequest() {
        final var request = createValidSendSmsRequest();

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, false);

        assertThat(mappedRequest.getOriginator()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getDestinationNumber()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getDeliveryPriority()).isNull();
        assertThat(mappedRequest.getMessage()).isEqualTo(request.getMessage());
        assertThat(mappedRequest.getFlashSms()).isNull();
    }

    @Test
    void testMapFromSendSmsRequestWithPriority() {
        final var request = createValidSendSmsRequest(req -> req.setPriority(NORMAL));

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, false);

        assertThat(mappedRequest.getDeliveryPriority()).isEqualTo(NORMAL.name().toLowerCase());
    }

    @Test
    void testMapFromSendSmsRequestAsFlashSms() {
        final var request = createValidSendSmsRequest(req -> req.setPriority(HIGH));

        final var mappedRequest = mapper.mapFromSendSmsRequest(request, true);

        assertThat(mappedRequest.getOriginator()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getDestinationNumber()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getDeliveryPriority()).isEqualTo(HIGH.name().toLowerCase());
        assertThat(mappedRequest.getMessage()).isEqualTo(request.getMessage());
        assertThat(mappedRequest.getFlashSms()).isTrue();
    }
}
