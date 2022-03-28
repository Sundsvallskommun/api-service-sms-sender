package se.sundsvall.smssender.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import se.sundsvall.smssender.integration.Provider;
import se.sundsvall.smssender.integration.SmsProperties;
import se.sundsvall.smssender.integration.linkmobility.LinkMobilityService;
import se.sundsvall.smssender.integration.telia.TeliaService;

@ActiveProfiles("junit")
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SmsResourceTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeliaService mockTeliaService;
    @MockBean
    private LinkMobilityService mockLinkMobilityService;
    @MockBean
    private SmsProperties mockSmsProperties;

    @Test
    void sendSms_givenValidRequest_withTeliaProvider_shouldReturn200_OK_and_true() throws Exception {
        when(mockSmsProperties.getProvider()).thenReturn(Provider.TELIA);
        when(mockTeliaService.sendSms(any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/send/sms")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest())))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

        verify(mockSmsProperties, times(1)).getProvider();
        verify(mockTeliaService, times(1)).sendSms(any());
    }

    @Test
    void sendSms_givenValidRequest_withLinkMobilityProvider_shouldReturn200_OK_and_true() throws Exception {
        when(mockSmsProperties.getProvider()).thenReturn(Provider.LINKMOBILITY);
        when(mockLinkMobilityService.sendSms(any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/send/sms")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest())))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

        verify(mockSmsProperties, times(1)).getProvider();
        verify(mockLinkMobilityService, times(1)).sendSms(any());
    }

    private SendSmsRequest validRequest() {
        return SendSmsRequest.builder()
            .withSender("sender")
            .withMessage("message")
            .withMobileNumber("+46701234567")
            .build();
    }
}
