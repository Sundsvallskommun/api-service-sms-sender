package se.sundsvall.smssender.api;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import se.sundsvall.smssender.Application;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProviderRouter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.model.Priority.HIGH;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class SmsResourceTests {

	private static final String MUNICIPALITY_ID = "2281";

	private static final String PATH = "/" + MUNICIPALITY_ID + "/send/sms";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private SmsProviderRouter mockSmsProviderRouter;

	@Captor
	private ArgumentCaptor<SendSmsRequest> requestCaptor;

	@Test
	void sendSms() {
		// Arrange
		final var request = createValidSendSmsRequest();
		when(mockSmsProviderRouter.sendSms(any(SendSmsRequest.class))).thenReturn(true);

		// Act
		webTestClient.post().uri(PATH)
			.contentType(APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isOk()
			.expectBody().jsonPath("$.sent").isEqualTo(true);

		// Assert
		verify(mockSmsProviderRouter).sendSms(requestCaptor.capture());
		assertThat(requestCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
	}

	@Test
	void sendFlashSms() {

		// Arrange
		final var request = createValidSendSmsRequest(req -> req.setPriority(HIGH));
		when(mockSmsProviderRouter.sendFlashSms(any(SendSmsRequest.class))).thenReturn(true);

		// Act
		webTestClient.post().uri(uriBuilder -> uriBuilder.path(PATH).queryParam("flash", "true").build())
			.contentType(APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isOk()
			.expectBody().jsonPath("$.sent").isEqualTo(true);

		// Assert
		verify(mockSmsProviderRouter).sendFlashSms(requestCaptor.capture());
		assertThat(requestCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
	}

	@Test
	void sendSmsWithInvalidMunicipalityId() {

		// Arrange
		final var request = createValidSendSmsRequest();

		// Act
		final var response = webTestClient.post().uri(PATH.replace(MUNICIPALITY_ID, "22-81"))
			.contentType(APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("sendSms.municipalityId", "not a valid municipality ID"));
	}

}
