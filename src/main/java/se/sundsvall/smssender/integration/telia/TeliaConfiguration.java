package se.sundsvall.smssender.integration.telia;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.client.RestTemplate;

import se.sundsvall.dept44.configuration.resttemplate.RestTemplateBuilder;

@Configuration
@EnableConfigurationProperties(TeliaProperties.class)
class TeliaConfiguration {

    private final TeliaProperties properties;

    TeliaConfiguration(TeliaProperties properties) {
        this.properties = properties;
    }

    @Bean("integration.telia.clientregistration")
    ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("teliaId")
            .tokenUri(properties.getTokenUrl())
            .clientId(properties.getClientId())
            .clientSecret(properties.getClientSecret())
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build();
    }

    @Bean("integration.telia.resttemplate")
    RestTemplate teliaClient(@Qualifier("integration.telia.clientregistration") ClientRegistration clientRegistration) {
        return new RestTemplateBuilder()
            .withBaseUrl(properties.getApiUrl())
            .withOAuth2Client(clientRegistration)
            .build();
    }
}
