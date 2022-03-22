package se.sundsvall.smssender.integration.telia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class TeliaResponseTest {

    @Test
    void testGetters() {
        TeliaResponse response = new TeliaResponse("0", "ok");

        assertThat(response.getStatusCode()).isEqualTo("0");
        assertThat(response.getStatusMessage()).isEqualTo("ok");
    }

    @Test
    void testSetters() {
        TeliaResponse response = new TeliaResponse();

        response.setStatusCode("0");
        response.setStatusMessage("ok");

        assertThat(response.getStatusCode()).isEqualTo("0");
        assertThat(response.getStatusMessage()).isEqualTo("ok");
    }
}
