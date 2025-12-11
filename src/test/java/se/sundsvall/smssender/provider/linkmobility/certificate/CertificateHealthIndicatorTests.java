package se.sundsvall.smssender.provider.linkmobility.certificate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Status;

@ExtendWith(MockitoExtension.class)
class CertificateHealthIndicatorTests {

	@InjectMocks
	private CertificateHealthIndicator certificateHealthIndicator;

	@Test
	void setUnhealthy() {
		certificateHealthIndicator.setUnhealthy();

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus().getCode()).isEqualTo("RESTRICTED");
		assertThat(health.getDetails().get("Reason")).isEqualTo("Unknown");
	}

	@Test
	void setUnhealthyWithReason() {
		certificateHealthIndicator.setUnhealthy("Shit happened");

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus().getCode()).isEqualTo("RESTRICTED");
		assertThat(health.getDetails().get("Reason")).isEqualTo("Shit happened");
	}

	@Test
	void setHealthy() {

		certificateHealthIndicator.setHealthy();

		var health = certificateHealthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

}
