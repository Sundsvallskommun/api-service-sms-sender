package se.sundsvall.smssender.integration;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

public enum Provider {
    TELIA("telia"),
    LINKMOBILITY("linkmobility");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    static Provider fromValue(final String value) {
        return Arrays.stream(values())
                .filter(provider -> provider.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> createUnknownProviderProblem(value));
    }

    private static ThrowableProblem createUnknownProviderProblem(String provider) {
        String validProviders = Arrays.stream(values())
                .map(Provider::getValue)
                .collect(Collectors.joining(","));

        String exceptionMessage = String.format("Unknown SMS integration provider '%s' "
                + "(valid providers: %s)", provider, validProviders);

        return Problem.builder().withDetail(exceptionMessage).withTitle("Unknown Provider").build();
    }
}
