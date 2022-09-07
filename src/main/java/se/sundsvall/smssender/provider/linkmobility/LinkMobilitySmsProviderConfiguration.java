package se.sundsvall.smssender.provider.linkmobility;


import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;

@Import(FeignConfiguration.class)
class LinkMobilitySmsProviderConfiguration {

    private final LinkMobilitySmsProviderProperties properties;

    LinkMobilitySmsProviderConfiguration(final LinkMobilitySmsProviderProperties properties) {
        this.properties = properties;
    }

    @Bean
    FeignBuilderCustomizer customizer() {
        return FeignMultiCustomizer.create()
            .withRequestInterceptor(new BasicAuthRequestInterceptor(
                properties.getBasicauth().getUsername(),
                properties.getBasicauth().getUsername()))
            .withRequestOptions(new Request.Options(
                properties.getConnectTimeout().toMillis(), TimeUnit.MILLISECONDS,
                properties.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS,
                true))
            .composeCustomizersToOne();
    }
}
