package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.model.Priority.HIGH;

import org.junit.jupiter.api.Test;

class TeliaMapperTests {

    private final TeliaMapper mapper = new TeliaMapper();

    @Test
    void testMapFromSendSmsRequest() {
        final var request = createValidSendSmsRequest();

        final var mappedRequest = mapper.mapFromSendSmsRequest(request);

        assertThat(mappedRequest.getOriginator()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getDestinationNumber()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getDeliveryPriority()).isNull();
        assertThat(mappedRequest.getMessage()).isEqualTo(request.getMessage());
    }

    @Test
    void testMapFromSendSmsRequestWithPriority() {
        final var request = createValidSendSmsRequest();
        request.setPriority(HIGH);

        final var mappedRequest = mapper.mapFromSendSmsRequest(request);

        assertThat(mappedRequest.getDeliveryPriority()).isEqualTo("high");
    }
}
