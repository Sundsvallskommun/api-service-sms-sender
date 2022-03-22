package se.sundsvall.smssender.integration.telia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class TeliaSendSmsRequestTest {

    @Test
    void testBuildersAndGetters() {
        TeliaSendSmsRequest request = TeliaSendSmsRequest.builder()
                .withMessage("message")
                .withDestinationNumber("+46701234567")
                .withDeliveryPriority("high")
                .withOriginator("Telia")
                .build();

        assertThat(request.getMessage()).isEqualTo("message");
        assertThat(request.getDeliveryPriority()).isEqualTo("high");
        assertThat(request.getDestinationNumber()).isEqualTo("+46701234567");
        assertThat(request.getOriginator()).isEqualTo("Telia");
    }
}
