package se.sundsvall.smssender.integration.linkmobility;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "integration.linkmobility")
@ConstructorBinding
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class LinkMobilityProperties {

    private String apiUrl;
    private String username;
    private String password;
    private String platformId;
    private String platformPartnerId;
}
