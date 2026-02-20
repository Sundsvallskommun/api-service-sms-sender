package se.sundsvall.smssender.provider.linkmobility.certificate;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CertificateHealthIndicator implements HealthIndicator {

	private final AtomicBoolean healthy = new AtomicBoolean(true);
	private String reason;

	public Health health() {
		return this.healthy.get() ? Health.up().build() : Health.status("RESTRICTED").withDetail("Reason", Objects.requireNonNullElse(this.reason, "Unknown")).build();
	}

	public void setUnhealthy() {
		this.healthy.set(false);
	}

	public void setUnhealthy(String reason) {
		this.setUnhealthy();
		this.reason = reason;
	}

	public void setHealthy() {
		this.healthy.set(true);
		this.reason = null;
	}
}
