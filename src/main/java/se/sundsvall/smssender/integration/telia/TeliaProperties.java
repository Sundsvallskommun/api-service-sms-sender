package se.sundsvall.smssender.integration.telia;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "integration.telia")
@ConstructorBinding
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class TeliaProperties {

    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final String apiUrl;

}
