package se.sundsvall.smssender.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import se.sundsvall.smssender.api.model.SendSmsRequest;
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

}
