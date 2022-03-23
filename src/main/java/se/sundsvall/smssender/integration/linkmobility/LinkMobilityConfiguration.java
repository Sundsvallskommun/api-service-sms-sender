package se.sundsvall.smssender.integration.linkmobility;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;

import se.sundsvall.dept44.configuration.resttemplate.RestTemplateBuilder;

@EnableConfigurationProperties(LinkMobilityProperties.class)
@Configuration
public class LinkMobilityConfiguration {

    private final LinkMobilityProperties properties;

    public LinkMobilityConfiguration(LinkMobilityProperties properties) {
        this.properties = properties;
    }

    @Bean("integration.linkmobility.resttemplate")
    public RestTemplate linkMobilityClient(Logbook logbook) {
        return new RestTemplateBuilder()
            .withBaseUrl(properties.getApiUrl())
            .withLogbook(logbook)
            .withBasicAuthentication(properties.getUsername(), properties.getPassword())
            .build();
    }
}
