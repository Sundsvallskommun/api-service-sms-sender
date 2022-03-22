package se.sundsvall.smssender.integration.linkmobility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.integration.linkmobility.domain.ResponseStatus;

@ActiveProfiles("junit")
class ResponseStatusTest {

    @Test
    void givenValidResponseStatusValueReturnsResponseStatus() {
        int statusSentValue = ResponseStatus.SENT.getValue();

        assertThat(ResponseStatus.forValue(statusSentValue)).isEqualTo(ResponseStatus.SENT);
    }

    @Test
    void givenInvalidResponseStatusIsNull() {
        int statusSentValue = -1;

        assertThat(ResponseStatus.forValue(statusSentValue)).isNull();
    }
}
