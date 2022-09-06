package se.sundsvall.smssender.provider.linkmobility.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus;

@ActiveProfiles("junit")
class LinkMobilitySmsResponseTests {

    @Test
    void testGetters() {
        var response = new LinkMobilitySmsResponse(ResponseStatus.SENT);

        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SENT);
    }

    @Test
    void testSetters() {
        var response = new LinkMobilitySmsResponse();
        response.setStatus(ResponseStatus.SENT);

        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SENT);

        // just for coverage
        assertThat(response.toString()).isNotNull();
    }

    @Test
    void givenValidResponseStatusValueReturnsResponseStatus() {
        var statusSentValue = ResponseStatus.SENT.getValue();

        assertThat(ResponseStatus.forValue(statusSentValue)).isEqualTo(ResponseStatus.SENT);
    }

    @Test
    void givenInvalidResponseStatusIsNull() {
        var statusSentValue = -1;

        assertThat(ResponseStatus.forValue(statusSentValue)).isNull();
    }
}
