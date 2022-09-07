package se.sundsvall.smssender.provider.telia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class TeliaSmsResponseTests {

    @Test
    void testGetters() {
        var response = new TeliaSmsResponse("0", "ok");

        assertThat(response.getStatusCode()).isEqualTo("0");
        assertThat(response.getStatusMessage()).isEqualTo("ok");
    }

    @Test
    void testSetters() {
        var response = new TeliaSmsResponse();

        response.setStatusCode("0");
        response.setStatusMessage("ok");

        assertThat(response.getStatusCode()).isEqualTo("0");
        assertThat(response.getStatusMessage()).isEqualTo("ok");
    }
}
