package se.sundsvall.smssender.integration.linkmobility;


import org.springframework.boot.context.properties.ConfigurationProperties;

import se.sundsvall.smssender.integration.SmsProviderProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "provider.linkmobility")
@Getter
@Setter
public class LinkMobilityProperties extends SmsProviderProperties {

    private String platformId;
    private String platformPartnerId;
    private BasicAuth basicAuth = new BasicAuth();
}
