package se.sundsvall.smssender.api.model;

import static se.sundsvall.smssender.TestDataFactory.createValidSender;
import static se.sundsvall.smssender.api.model.RequestValidationAssertions.SenderAssertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
			.hasConstraintViolation("name", "must not be blank")
			.hasConstraintViolation("name", "sender must be between 3-11 characters (allowed characters: a-z, A-Z, 0-9, whitespace) and start with a non-numeric character");
	}

	@Test
	void shouldFailForNullName() {
		validSender.setName(null);
		assertThat(validSender)
			.hasSingleConstraintViolation("name", "must not be blank");
	}

	@ParameterizedTest
	@ValueSource(strings = {"a", "321ab", "abcdefghijkl", "abcdefghijklmno", "  abc"})
	void shouldFailForInvalidName(final String name) {
		validSender.setName(name);
		assertThat(validSender)
			.hasSingleConstraintViolation("name", "sender must be between 3-11 characters (allowed characters: a-z, A-Z, 0-9, whitespace) and start with a non-numeric character");
	}

	@ParameterizedTest
	@ValueSource(strings = {"abc", "abc12", "Min Bankman"})
	void shouldPassWithValidName(final String name) {
		validSender.setName(name);
		assertThat(validSender).hasNoConstraintViolations();
	}

}
