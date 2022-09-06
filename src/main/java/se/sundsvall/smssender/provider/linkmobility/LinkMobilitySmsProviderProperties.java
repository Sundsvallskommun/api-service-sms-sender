package se.sundsvall.smssender.provider.linkmobility;


import org.springframework.boot.context.properties.ConfigurationProperties;

import se.sundsvall.smssender.provider.SmsProviderProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "provider.linkmobility")
public class LinkMobilitySmsProviderProperties extends SmsProviderProperties {

    private String platformId;
    private String platformPartnerId;
    private BasicAuth basicauth = new BasicAuth();
}
