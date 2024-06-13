package se.sundsvall.smssender.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.smssender.api.model.SendSmsRequest;

class SmsProviderTests {

	@Test
	void testVerifyFlashCapability() {
		// Test a flash-capable SMS provider
		assertThatNoException().isThrownBy(() -> new DummySmsProvider(true).verifyFlashCapability(true));
		assertThatNoException().isThrownBy(() -> new DummySmsProvider(true).verifyFlashCapability(false));

		final var dummySmsProvider = new DummySmsProvider(false);

		// Test a non-flash-capable SMS provider
		assertThatExceptionOfType(ThrowableProblem.class)
			.isThrownBy(() -> dummySmsProvider.verifyFlashCapability(true));
		assertThatNoException().isThrownBy(() -> dummySmsProvider.verifyFlashCapability(false));
	}

	@Test
	void testCompareTo() {
		final var provider1 = new DummySmsProvider(50);
		final var provider2 = new DummySmsProvider(25);
		final var provider3 = new DummySmsProvider(75);
		final var provider4 = new DummySmsProvider(75);

		assertThat(provider1).isGreaterThan(provider2);
		assertThat(provider2).isLessThan(provider3);
		assertThat(provider3).isEqualByComparingTo(provider4);
	}

	static class DummySmsProvider implements SmsProvider {

		private final boolean flashCapable;
		private final int priority;

		DummySmsProvider(final boolean flashCapable) {
			this(flashCapable, -1);
		}

		DummySmsProvider(final int priority) {
			this(false, priority);
		}

		DummySmsProvider(final boolean flashCapable, final int priority) {
			this.flashCapable = flashCapable;
			this.priority = priority;
		}

		@Override
		public boolean sendSms(final SendSmsRequest request, final boolean flash) {
			// Do nothing
			return false;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public boolean isFlashSmsCapable() {
			return flashCapable;
		}

		@Override
		public String getName() {
			return "dummy";
		}

		@Override
		public int getPriority() {
			return priority;
		}
	}
}
