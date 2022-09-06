package se.sundsvall.smssender.provider.linkmobility;


import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignHelper;

import feign.RequestInterceptor;

@Import(FeignConfiguration.class)
class LinkMobilitySmsProviderConfiguration {

    private final LinkMobilitySmsProviderProperties properties;

    LinkMobilitySmsProviderConfiguration(final LinkMobilitySmsProviderProperties properties) {
        this.properties = properties;
    }

    @Bean
    RequestInterceptor oAuth2RequestInterceptor() {
        return FeignHelper.basicAuthInterceptor(
            properties.getBasicauth().getUsername(), properties.getBasicauth().getPassword());
    }

    @Bean
    FeignBuilderCustomizer customizer() {
        return FeignHelper.customizeRequestOptions()
            .withConnectTimeout(properties.getConnectTimeout())
            .withReadTimeout(properties.getReadTimeout())
            .build();
    }
}
