package se.sundsvall.smssender.provider;

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

        // Test a non-flash-capable SMS provider
        assertThatExceptionOfType(ThrowableProblem.class)
            .isThrownBy(() -> new DummySmsProvider(false).verifyFlashCapability(true));
        assertThatNoException().isThrownBy(() -> new DummySmsProvider(false).verifyFlashCapability(false));
    }

    static class DummySmsProvider implements SmsProvider {

        private final boolean flashCapable;

        DummySmsProvider(final boolean flashCapable) {
            this.flashCapable = flashCapable;
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
            return -1;
        }
    }
}
