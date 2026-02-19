package se.sundsvall.smssender.api.util;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.sundsvall.smssender.api.model.Sender;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.api.util.RequestCleaner.cleanMessage;
import static se.sundsvall.smssender.api.util.RequestCleaner.cleanMobileNumber;
import static se.sundsvall.smssender.api.util.RequestCleaner.cleanSenderName;

class RequestCleanerTests {

	@ParameterizedTest
	@MethodSource("messageProvider")
	void cleanMessage_replacesCarriageReturns(final String input, final String expected) {
		final var result = cleanMessage(input);
		assertThat(result).isEqualTo(expected);
	}

	@ParameterizedTest
	@MethodSource("swedishCharacterStringProvider")
	void cleanSenderName_replacesSwedishCharacters(final String input, final String expected) {
		var sender = Sender.builder()
			.withName(input)
			.build();
		var cleanedSender = cleanSenderName(sender);
		assertThat(cleanedSender.getName()).isEqualTo(expected);
	}

	@ParameterizedTest()
	@MethodSource("mobileNumberProvider")
	void cleanMobileNumber_cleansIllegalNumberCorrectly(final String input, final String expected) {
		String result = cleanMobileNumber(input);
		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> messageProvider() {
		return Stream.of(
			Arguments.of("hello\r\nworld", "hello\nworld"),
			Arguments.of("line1\r\nline2\r\nline3", "line1\nline2\nline3"),
			Arguments.of("no carriage returns", "no carriage returns"),
			Arguments.of("already\nunix", "already\nunix"),
			Arguments.of("mixed\r\ncarriage\nreturns", "mixed\ncarriage\nreturns"),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> swedishCharacterStringProvider() {
		return Stream.of(
			Arguments.of("ändra", "andra"),
			Arguments.of("ådra", "adra"),
			Arguments.of("övriga", "ovriga"),
			Arguments.of("Är", "Ar"),
			Arguments.of("År", "Ar"),
			Arguments.of("Öra", "Ora"));
	}

	private static Stream<Arguments> mobileNumberProvider() {
		return Stream.of(
			Arguments.of("070-174 06 05", "+46701740605"),
			Arguments.of("0701740605", "+46701740605"),
			Arguments.of("123", "123"),
			Arguments.of("+46", "+46"),
			Arguments.of("00 46 70 174 06 05", "+46701740605"),
			Arguments.of("+46071740605", "+4671740605"));
	}

}
