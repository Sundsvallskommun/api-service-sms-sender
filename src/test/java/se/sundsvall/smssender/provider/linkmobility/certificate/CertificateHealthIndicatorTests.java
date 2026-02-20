package se.sundsvall.smssender.provider.linkmobility.certificate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.health.contributor.Status.UP;

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
		assertThat(health.getStatus()).isEqualTo(UP);
	}
}
