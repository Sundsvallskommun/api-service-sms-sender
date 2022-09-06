package se.sundsvall.smssender.integration;

import java.time.Duration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SmsProviderProperties {

    private String baseUrl;

    private Duration readTimeout = Duration.ofSeconds(15);
    private Duration connectTimeout = Duration.ofSeconds(5);

    @Getter
    @Setter
    public static class OAuth2 {

        private String tokenUrl;
        private String clientId;
        private String clientSecret;
        private String grantType = "client_credentials";
    }

    @Getter
    @Setter
    public static class BasicAuth {

        private String username;
        private String password;
    }
}
