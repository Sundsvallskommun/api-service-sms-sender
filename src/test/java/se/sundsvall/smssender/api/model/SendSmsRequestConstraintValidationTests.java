package se.sundsvall.smssender.api.model;

import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.api.model.RequestValidationAssertions.SendSmsRequestAssertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SendSmsRequestConstraintValidationTests {

	private final SendSmsRequest validRequest = createValidSendSmsRequest();

	@Test
	void shouldPassForValidRequest() {
		assertThat(validRequest).hasNoConstraintViolations();
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"070-174 06 05", "0701740605", "46701740605", "123", "+46"
	})
	void shouldFailForInvalidMobileNumber(String mobileNumber) {
		validRequest.setMobileNumber(mobileNumber);
		assertThat(validRequest)
			.hasSingleConstraintViolation("mobileNumber", "must be a valid MSISDN (example: +46701740605). Regular expression: ^\\+[1-9][\\d]{3,14}$");
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
