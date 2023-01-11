package se.sundsvall.smssender.provider.telia;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

import org.junit.jupiter.api.Test;

class TeliaMapperTests {

    private final TeliaMapper mapper = new TeliaMapper();

    @Test
    void testMapFromSendSmsRequest() {
        var request = createValidSendSmsRequest();

        var mappedRequest = mapper.mapFromSendSmsRequest(request);

        assertThat(mappedRequest.getOriginator()).isEqualTo(request.getSender().getName());
        assertThat(mappedRequest.getDestinationNumber()).isEqualTo(request.getMobileNumber());
        assertThat(mappedRequest.getMessage()).isEqualTo(request.getMessage());
    }
}
