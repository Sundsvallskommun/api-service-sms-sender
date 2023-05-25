package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

import org.junit.jupiter.api.Test;

import se.sundsvall.smssender.model.Priority;

class TeliaMapperTests {

    private final TeliaMapper mapper = new TeliaMapper();

    @Test
    void testMapFromSendSmsRequest() {
        var request = createValidSendSmsRequest();

        var mappedRequest = mapper.mapFromSendSmsRequest(request);

        assertThat(mappedRequest.getOriginator()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getDestinationNumber()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getDeliveryPriority()).isNull();
        assertThat(mappedRequest.getMessage()).isEqualTo(request.getMessage());
    }

    @Test
    void testMapFromSendSmsRequestWithPriority() {
        var request = createValidSendSmsRequest();
        request.setPriority(Priority.HIGH);

        var mappedRequest = mapper.mapFromSendSmsRequest(request);

        assertThat(mappedRequest.getDeliveryPriority()).isEqualTo("high");
    }
}
