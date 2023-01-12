package se.sundsvall.smssender.provider.linkmobility.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    }

    @Test
    void testResponseStatus_forValue_OK() {
        var statusSentValue = ResponseStatus.SENT.getValue();

        assertThat(ResponseStatus.forValue(statusSentValue)).isEqualTo(ResponseStatus.SENT);
    }

    @Test
    void testResponseStatus_forValue_unknownValue() {
        assertThat(ResponseStatus.forValue(-1)).isNull();
    }

    /*
     * A test that is here just to make sure the JSON deserialization doesn't break as it did with
     * the implicit upgrade from Jackson 2.10 to a later version.
     */
    @Test
    void testDeserializationWorksAsExpected() throws Exception {
        var json = "{\"messageId\":\"Tw7CnpuE6jHAZQQ7ghCYa9\",\"resultCode\":1005,\"description\":\"Queued\"}";

        var objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var response = objectMapper.readValue(json, LinkMobilitySmsResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.QUEUED);
    }
}
