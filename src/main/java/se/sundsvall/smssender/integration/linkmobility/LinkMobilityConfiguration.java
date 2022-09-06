package se.sundsvall.smssender.integration.linkmobility;


import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignHelper;
import se.sundsvall.dept44.configuration.feign.decoder.ProblemErrorDecoder;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Import(FeignConfiguration.class)
class LinkMobilityConfiguration {

    private final LinkMobilityProperties properties;

    LinkMobilityConfiguration(final LinkMobilityProperties properties) {
        this.properties = properties;
    }

    @Bean
    RequestInterceptor oAuth2RequestInterceptor() {
        return FeignHelper.basicAuthInterceptor(
            properties.getBasicAuth().getUsername(), properties.getBasicAuth().getPassword());
    }

    @Bean
    FeignBuilderCustomizer customizer() {
        return FeignHelper.customizeRequestOptions()
            .withConnectTimeout(properties.getConnectTimeout())
            .withReadTimeout(properties.getReadTimeout())
            .build();
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new ProblemErrorDecoder(LinkMobilitySmsProvider.PROVIDER_NAME);
    }
}
