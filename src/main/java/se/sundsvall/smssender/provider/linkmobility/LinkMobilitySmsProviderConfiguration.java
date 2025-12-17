package se.sundsvall.smssender.provider.linkmobility;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;

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
				properties.getBasicAuth().getUsername(),
				properties.getBasicAuth().getPassword()))
			.withRequestOptions(new Request.Options(
				properties.getConnectTimeout().toMillis(), MILLISECONDS,
				properties.getReadTimeout().toMillis(), MILLISECONDS,
				true))
			.composeCustomizersToOne();
	}
}
