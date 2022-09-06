package se.sundsvall.smssender.provider.telia;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignHelper;

import feign.RequestInterceptor;

@Import(FeignConfiguration.class)
class TeliaSmsProviderConfiguration {

    private final TeliaSmsProviderProperties properties;

    TeliaSmsProviderConfiguration(final TeliaSmsProviderProperties properties) {
        this.properties = properties;
    }


    @Bean
    RequestInterceptor oAuth2RequestInterceptor() {
        return FeignHelper.oAuth2RequestInterceptor(ClientRegistration
            .withRegistrationId(TeliaSmsProvider.PROVIDER_NAME)
            .tokenUri(properties.getOauth2().getTokenUrl())
            .clientId(properties.getOauth2().getClientId())
            .clientSecret(properties.getOauth2().getClientSecret())
            .authorizationGrantType(new AuthorizationGrantType(properties.getOauth2().getGrantType()))
            .build());
    }


    @Bean
    FeignBuilderCustomizer customizer() {
        return FeignHelper.customizeRequestOptions()
            .withConnectTimeout(properties.getConnectTimeout())
            .withReadTimeout(properties.getReadTimeout())
            .build();
    }

}
