package se.sundsvall.smssender.api.model;

import static se.sundsvall.smssender.TestDataFactory.createValidSendFlashSmsRequest;
import static se.sundsvall.smssender.api.model.RequestValidationAssertions.SendFlashSmsRequestAssertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SendFlashSmsRequestConstraintValidationTests {

	private final SendFlashSmsRequest validRequest = createValidSendFlashSmsRequest();

	@Test
	void shouldPassForValidRequest() {
		assertThat(validRequest).hasNoConstraintViolations();
	}

	@Test
	void shouldFailForBlankMobileNumber() {
		validRequest.setMobileNumber("");
		assertThat(validRequest)
			.hasSingleConstraintViolation("mobileNumber", "must not be blank");
	}

	@Test
	void shouldFailForNullMobileNumber() {
		validRequest.setMobileNumber(null);
		assertThat(validRequest)
			.hasSingleConstraintViolation("mobileNumber", "must not be blank");
	}

	@Test
	void shouldFailForBlankMessage() {
		validRequest.setMessage("");
		assertThat(validRequest)
			.hasSingleConstraintViolation("message", "must not be blank");
	}

	@Test
	void shouldFailForNullMessage() {
		validRequest.setMessage(null);
		assertThat(validRequest)
			.hasSingleConstraintViolation("message", "must not be blank");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"a", "ab", "abcdefghijkl"
	})
	void shouldFailForInvalidSender(final String name) {
		var sender = new Sender(name);
		validRequest.setSender(sender);
		assertThat(validRequest)
			.hasSingleConstraintViolation("sender.name", "size must be between 3 and 11");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"abc", "abc12", "Min Bankman"
	})
	void shouldPassWithValidSender(final String name) {
		var sender = new Sender(name);
		validRequest.setSender(sender);
		assertThat(validRequest).hasNoConstraintViolations();
	}

	@Test
	void shouldFailWithNullSender() {
		validRequest.setSender(null);
		assertThat(validRequest)
			.hasSingleConstraintViolation("sender", "must not be null");
	}

	@Test
	void shouldFailWithBlankSender() {
		var sender = new Sender("");
		validRequest.setSender(sender);
		assertThat(validRequest)
			.hasConstraintViolation("sender.name", "must not be blank");
	}

}
