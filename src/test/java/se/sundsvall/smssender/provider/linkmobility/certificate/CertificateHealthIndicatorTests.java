package se.sundsvall.smssender.provider.linkmobility.certificate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

class CertificateHealthIndicatorTests {

	private final CertificateHealthIndicator certificateHealthIndicator = new CertificateHealthIndicator();

	@Test
	void setUnhealthy() {
		certificateHealthIndicator.setUnhealthy();

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus().getCode()).isEqualTo("RESTRICTED");
		assertThat(health.getDetails()).containsEntry("Reason", "Unknown");
	}

	@Test
	void setUnhealthyWithReason() {
		certificateHealthIndicator.setUnhealthy("Shit happened");

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus().getCode()).isEqualTo("RESTRICTED");
		assertThat(health.getDetails()).containsEntry("Reason", "Shit happened");
	}

	@Test
	void setHealthy() {

		certificateHealthIndicator.setHealthy();

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

}
