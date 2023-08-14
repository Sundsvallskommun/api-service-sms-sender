package se.sundsvall.smssender.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SmsExceptionTests {

    @Test
    void testExceptionWithMessage() {
        final var message = "some exception message";

        assertThat(new SmsException(message)).hasMessage(message);
    }

    @Test
    void testExceptionWithMessageAndCause() {
        final var message = "some exception message";
        final var exception = new RuntimeException("some cause exception message");

        assertThat(new SmsException(message, exception))
            .hasMessage(message)
            .hasCause(exception);
    }
}
