package se.sundsvall.smssender.integration;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.zalando.problem.ThrowableProblem;

@ActiveProfiles("junit")
class ProviderTest {

    @Test
    void createUnknownProvider_returnsIllegalArgument() {
        String faultyProvider = "notAProvider";
        assertThatThrownBy(() -> Provider.fromValue(faultyProvider)).isInstanceOf(ThrowableProblem.class);
    }

    @Test
    void createUnknownProvider_returnsIllegalArgument_v2() {
        assertThatExceptionOfType(ThrowableProblem.class)
                .isThrownBy(() -> Provider.fromValue("invalidValue"));
    }
}
