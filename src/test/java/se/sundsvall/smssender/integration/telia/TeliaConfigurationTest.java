package se.sundsvall.smssender.integration.telia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("junit")
@SpringBootTest(classes = TeliaConfiguration.class)
class TeliaConfigurationTest {

    @Autowired
    private ClientRegistration clientRegistration;

    @Autowired
    @Qualifier("integration.telia.resttemplate")
    private RestTemplate restTemplate;

    @Test
    void clientRegistration_bean_isNotNull() {
        assertThat(clientRegistration).isNotNull();
    }

    @Test
    void restTemplate_bean_isNotNull() {
        assertThat(restTemplate).isNotNull();
    }
}
