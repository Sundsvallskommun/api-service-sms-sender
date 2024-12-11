package se.sundsvall.smssender.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SmsProviderProperties {

	@NotBlank
	private String baseUrl;

	@NotNull
	@Positive
	private int priority;

	private boolean enabled = true;
	private boolean flashCapable = false;

	private Duration readTimeout = Duration.ofSeconds(15);
	private Duration connectTimeout = Duration.ofSeconds(5);

	@Getter
	@Setter
	public static class OAuth2 {

		@NotBlank
		private String tokenUrl;
		@NotBlank
		private String clientId;
		@NotBlank
		private String clientSecret;
		private String grantType = "client_credentials";
	}

	@Getter
	@Setter
	public static class BasicAuth {

		@NotBlank
		private String username;
		@NotBlank
		private String password;
	}
}
