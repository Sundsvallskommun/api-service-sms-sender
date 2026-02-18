package se.sundsvall.smssender.api.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.function.Predicate;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class RequestValidationAssertions<R> extends AbstractAssert<RequestValidationAssertions<R>, R> {

	private final Validator validator;
	private final List<ConstraintViolation<R>> constraintViolations;

	protected RequestValidationAssertions(final R request, final Class<?> requestType) {
		super(request, requestType);

		validator = Validation.buildDefaultValidatorFactory().getValidator();
		constraintViolations = List.copyOf(validator.validate(request));
	}

	void hasNoConstraintViolations() {
		isNotNull();

		assertThat(constraintViolations).isEmpty();
	}

	void hasSingleConstraintViolation(final String propertyPath,
		final String message) {
		isNotNull();

		assertThat(constraintViolations).hasSize(1);

		var constraintViolation = constraintViolations.get(0);
		assertThat(propertyPath).isEqualTo(constraintViolation.getPropertyPath().toString());
		assertThat(message).isEqualTo(constraintViolation.getMessage());
	}

	void hasSingleConstraintViolation(final String propertyPath,
		final Predicate<String> messagePredicate) {
		isNotNull();

		assertThat(constraintViolations).hasSize(1);

		var constraintViolation = constraintViolations.get(0);
		assertThat(propertyPath).isEqualTo(constraintViolation.getPropertyPath().toString());
		assertThat(messagePredicate).accepts(constraintViolation.getMessage());
	}

	RequestValidationAssertions<R> hasConstraintViolation(final String propertyPath,
		final String message) {
		isNotNull();

		assertThat(constraintViolations).isNotEmpty();

		var matchingConstraintViolation = constraintViolations.stream()
			.filter(constraintViolation -> constraintViolation.getPropertyPath().toString().equals(propertyPath)
				&& constraintViolation.getMessage().equals(message))
			.findFirst();

		if (matchingConstraintViolation.isEmpty()) {
			failWithMessage(String.format("Expected a constraint violation on '%s' with the message '%s' to exist", propertyPath, message));
		}

		return this;
	}

	static class SendSmsRequestAssertions extends RequestValidationAssertions<SendSmsRequest> {

		private SendSmsRequestAssertions(final SendSmsRequest request) {
			super(request, SendSmsRequestAssertions.class);
		}

		static SendSmsRequestAssertions assertThat(final SendSmsRequest request) {
			return new SendSmsRequestAssertions(request);
		}
	}

	static class SendFlashSmsRequestAssertions extends RequestValidationAssertions<SendFlashSmsRequest> {

		private SendFlashSmsRequestAssertions(final SendFlashSmsRequest request) {
			super(request, SendFlashSmsRequestAssertions.class);
		}

		static SendFlashSmsRequestAssertions assertThat(final SendFlashSmsRequest request) {
			return new SendFlashSmsRequestAssertions(request);
		}
	}

	static class SenderAssertions extends RequestValidationAssertions<Sender> {

		private SenderAssertions(final Sender sender) {
			super(sender, SenderAssertions.class);
		}

		static SenderAssertions assertThat(final Sender sender) {
			return new SenderAssertions(sender);
		}
	}
}
