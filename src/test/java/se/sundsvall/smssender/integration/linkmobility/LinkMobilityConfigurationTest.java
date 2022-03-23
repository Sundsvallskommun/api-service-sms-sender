package se.sundsvall.smssender.integration.linkmobility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;

@ActiveProfiles("junit")
@SpringBootTest(classes = LinkMobilityConfiguration.class)
class LinkMobilityConfigurationTest {

    @MockBean
    private Logbook logbook;

    @Autowired
    @Qualifier("integration.linkmobility.resttemplate")
    private RestTemplate restTemplate;

    @Test
    void restTemplate_bean_isNotNull() {
        assertThat(restTemplate).isNotNull();
    }
}
