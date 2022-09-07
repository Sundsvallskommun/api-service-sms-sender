package se.sundsvall.smssender.provider.telia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class TeliaSmsRequestTests {

    @Test
    void testBuildersAndGetters() {
        var request = TeliaSmsRequest.builder()
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
