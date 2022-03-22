package se.sundsvall.smssender.integration.linkmobility.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class LinkMobilityResponseTest {

    @Test
    void testGetters() {
        LinkMobilityResponse response = new LinkMobilityResponse(ResponseStatus.SENT);

        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SENT);
    }

    @Test
    void testSetters() {
        LinkMobilityResponse response = new LinkMobilityResponse();
        response.setStatus(ResponseStatus.SENT);

        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SENT);
        // just for coverage
        assertThat(response.toString()).isNotNull();
    }
}
