package se.sundsvall.smssender.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;
import se.sundsvall.smssender.integration.SmsRouter;

@ActiveProfiles("junit")
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SmsResourceTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SmsRouter mockSmsRouter;

    @Test
    void sendSms_givenValidRequest_withTeliaProvider_shouldReturn200_OK_and_true() throws Exception {
        when(mockSmsRouter.sendSms(any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/send/sms")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sent").value(true));

        verify(mockSmsRouter, times(1)).sendSms(any());
    }

    @Test
    void sendSms_givenValidRequest_withLinkMobilityProvider_shouldReturn200_OK_and_true() throws Exception {
        when(mockSmsRouter.sendSms(any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/send/sms")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sent").value(true));

        verify(mockSmsRouter, times(1)).sendSms(any());
    }

    private SendSmsRequest validRequest() {
        return SendSmsRequest.builder()
            .withSender(Sender.builder()
                .withName("sender")
                .build())
            .withMessage("message")
            .withMobileNumber("0701234567")
            .build();
    }
}
