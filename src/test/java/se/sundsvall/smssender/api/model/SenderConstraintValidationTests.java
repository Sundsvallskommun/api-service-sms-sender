package se.sundsvall.smssender.api.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static se.sundsvall.smssender.TestDataFactory.createValidSender;
import static se.sundsvall.smssender.api.model.RequestValidationAssertions.SenderAssertions.assertThat;

class SenderConstraintValidationTests {

	private final Sender validSender = createValidSender();

	@Test
	void shouldPassForValidSender() {
		assertThat(validSender).hasNoConstraintViolations();
	}

	@Test
	void shouldFailForBlankName() {
		validSender.setName("");
		assertThat(validSender)
			.hasConstraintViolation("name", "must not be blank");
	}

	@Test
	void shouldFailForNullName() {
		validSender.setName(null);
		assertThat(validSender)
			.hasSingleConstraintViolation("name", "must not be blank");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"a", "ab", "abcdefghijkl"
	})
	void shouldFailForInvalidName(final String name) {
		validSender.setName(name);
		assertThat(validSender)
			.hasSingleConstraintViolation("name", "size must be between 3 and 11");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"abc", "abc12", "Min Bankman"
	})
	void shouldPassWithValidName(final String name) {
		validSender.setName(name);
		assertThat(validSender).hasNoConstraintViolations();
	}

}
