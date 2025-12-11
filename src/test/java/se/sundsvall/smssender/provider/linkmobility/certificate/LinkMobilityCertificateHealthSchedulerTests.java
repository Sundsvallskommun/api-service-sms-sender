package se.sundsvall.smssender.provider.linkmobility.certificate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sundsvall.smssender.provider.linkmobility.LinkMobilityIntegration;

@ExtendWith(MockitoExtension.class)
class LinkMobilityCertificateHealthSchedulerTests {

	@Mock
	private LinkMobilityIntegration linkMobilityIntegrationMock;

	@Mock
	private CertificateHealthIndicator certificateHealthIndicatorMock;

	@InjectMocks
	private LinkMobilityCertificateHealthScheduler scheduler;

	@Test
	void healthCheck_healthy() {
		scheduler.healthCheck();

		verify(linkMobilityIntegrationMock).healthCheck();
		verify(certificateHealthIndicatorMock).setHealthy();
	}

	@Test
	void healthCheck_unhealthy() {
		doThrow(new RuntimeException("ERROR LOL")).when(linkMobilityIntegrationMock).healthCheck();

		scheduler.healthCheck();

		verify(linkMobilityIntegrationMock).healthCheck();
		verify(certificateHealthIndicatorMock).setUnhealthy("An error occurred during the certificate health check");
	}
}
