package se.sundsvall.smssender.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class SmsPropertyTest {

    @Test
    void getProviderGivenValidStringProvider() {
        String provider = Provider.TELIA.getValue();
        SmsProperties props = new SmsProperties(provider);

        assertThat(props.getProvider()).isEqualTo(Provider.TELIA);
    }
}
