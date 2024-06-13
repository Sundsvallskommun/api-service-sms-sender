package se.sundsvall.smssender.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;
import se.sundsvall.smssender.provider.SmsProviderRouter;

@ActiveProfiles("junit")
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SmsResourceTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SmsProviderRouter mockSmsProviderRouter;

	private final SmsResource smsResource = new SmsResource(null);

	@Test
	void sendSms_OK() throws Exception {
		when(mockSmsProviderRouter.sendSms(any(SendSmsRequest.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders
				.post("/send/sms")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createValidSendSmsRequest())))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.sent").value(true));

		verify(mockSmsProviderRouter, times(1)).sendSms(any(SendSmsRequest.class));
	}

	@Test
	void sendFlashSms_OK() throws Exception {
		when(mockSmsProviderRouter.sendFlashSms(any(SendSmsRequest.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders
				.post("/send/sms")
				.param("flash", "true")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createValidSendSmsRequest())))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.sent").value(true));

		verify(mockSmsProviderRouter, times(1)).sendFlashSms(any(SendSmsRequest.class));

	}

	@ParameterizedTest
	@MethodSource("swedishCharacterStringProvider")
	void cleanSenderName_ReplacesSwedishCharacters(String input, String expected) {
		var sender = Sender.builder()
			.withName(input)
			.build();
		var cleanedSender = smsResource.cleanSenderName(sender);
		assertThat(cleanedSender.getName()).isEqualTo(expected);
	}

	@ParameterizedTest
	@MethodSource("mobileNumberProvider")
	void cleanMobileNumber_CleansNumberCorrectly(String input, String expected) {
		String result = smsResource.cleanMobileNumber(input);
		assertThat(result).isEqualTo(expected);
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
			Arguments.of("070-123 45 67", "+46701234567"),
			Arguments.of("0701234567", "+46701234567"),
			Arguments.of("123", "123"),
			Arguments.of("+46", "+46"),
			Arguments.of("00 46 70 123 45 67", "+46701234567"),
			Arguments.of("+46071234567", "+4671234567")
		);
	}

}
