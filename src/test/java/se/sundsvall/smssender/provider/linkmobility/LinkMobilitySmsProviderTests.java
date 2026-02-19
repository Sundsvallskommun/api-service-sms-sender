package se.sundsvall.smssender.provider.linkmobility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus.SENT;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class LinkMobilitySmsProviderTests {

	@Mock
	private LinkMobilityIntegration linkMobilityIntegrationMock;

	@InjectMocks
	private LinkMobilitySmsProvider linkMobilitySmsProvider;

	@Test
	void testSendSms_noFlash_OK() {
		final var smsRequest = createValidSendSmsRequest();
		final var flash = false;
		final var response = new LinkMobilitySmsResponse();
		response.setStatus(SENT);

		when(linkMobilityIntegrationMock.sendSms(smsRequest, flash)).thenReturn(response);

		var result = linkMobilitySmsProvider.sendSms(smsRequest, flash);

		assertThat(result).isTrue();
		verify(linkMobilityIntegrationMock).sendSms(smsRequest, flash);
	}
}
