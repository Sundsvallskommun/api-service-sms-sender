package se.sundsvall.smssender.provider.linkmobility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;
import static se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse.ResponseStatus.SENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class LinkMobilitySmsProviderTests {

	@Mock
	private LinkMobilitySmsProviderProperties mockProperties;
	@Mock
	private LinkMobilityClient mockClient;

	private LinkMobilitySmsProvider provider;

	@BeforeEach
	void setUp() {
		when(mockProperties.getPlatformId()).thenReturn("platformId");
		when(mockProperties.getPlatformPartnerId()).thenReturn("platformPartnerId");

		provider = new LinkMobilitySmsProvider(mockProperties, mockClient);
	}

	@Test
	void testSendSms_noFlash_OK() {
		final var response = new LinkMobilitySmsResponse();
		response.setStatus(SENT);

		when(mockClient.send(any(LinkMobilitySmsRequest.class))).thenReturn(response);

		assertThat(provider.sendSms(createValidSendSmsRequest(), false)).isTrue();

		verify(mockClient, times(1)).send(any(LinkMobilitySmsRequest.class));
	}
}
