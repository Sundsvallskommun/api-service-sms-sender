package se.sundsvall.smssender.provider.linkmobility.domain;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus.SENT;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@ActiveProfiles("junit")
class LinkMobilitySmsResponseTests {

	@Test
	void testGetters() {
		final var response = new LinkMobilitySmsResponse(SENT);

		assertThat(response.getStatus()).isEqualTo(SENT);
	}

	@Test
	void testSetters() {
		final var response = new LinkMobilitySmsResponse();
		response.setStatus(SENT);

		assertThat(response.getStatus()).isEqualTo(SENT);
	}

	@Test
	void testResponseStatus_forValue_OK() {
		final var statusSentValue = SENT.getValue();

		assertThat(ResponseStatus.forValue(statusSentValue)).isEqualTo(SENT);
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
		final var json = "{\"messageId\":\"Tw7CnpuE6jHAZQQ7ghCYa9\",\"resultCode\":1005,\"description\":\"Queued\"}";

		final var objectMapper = JsonMapper.builder().configure(FAIL_ON_UNKNOWN_PROPERTIES, false).build();
		final var response = objectMapper.readValue(json, LinkMobilitySmsResponse.class);

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(ResponseStatus.QUEUED);
	}
}
