package se.sundsvall.smssender.provider.linkmobility.certificate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.sundsvall.smssender.provider.linkmobility.LinkMobilityIntegration;

@Component
public class LinkMobilityCertificateHealthScheduler {

	private static final String ERROR_MESSAGE = "An error occurred during the certificate health check";
	private final LinkMobilityIntegration linkMobilityIntegration;

	private final CertificateHealthIndicator certificateHealthIndicator;

	public LinkMobilityCertificateHealthScheduler(final LinkMobilityIntegration linkMobilityIntegration, final CertificateHealthIndicator certificateHealthIndicator) {
		this.linkMobilityIntegration = linkMobilityIntegration;
		this.certificateHealthIndicator = certificateHealthIndicator;
	}

	@Scheduled(cron = "${scheduler.linkmobility.health.cron}")
	public void healthCheck() {
		try {
			linkMobilityIntegration.healthCheck();
			certificateHealthIndicator.setHealthy();
		} catch (Exception e) {
			certificateHealthIndicator.setUnhealthy(ERROR_MESSAGE);
		}
	}

}
