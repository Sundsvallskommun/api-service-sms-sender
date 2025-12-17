package se.sundsvall.smssender.provider.linkmobility;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;

@ExtendWith(MockitoExtension.class)
class LinkMobilityIntegrationTests {

	@Mock
	private LinkMobilityClient linkMobilityClientMock;

	@Mock
	private LinkMobilityMapper linkMobilityMapperMock;

	@InjectMocks
	private LinkMobilityIntegration linkMobilityIntegration;

	@Test
	void sendSms() {
		var sendSmsRequest = new SendSmsRequest();
		var flash = false;
		var linkMobilitySmsRequest = new LinkMobilitySmsRequest();

		when(linkMobilityMapperMock.toLinkMobilitySmsRequest(sendSmsRequest, flash)).thenReturn(linkMobilitySmsRequest);

		linkMobilityIntegration.sendSms(sendSmsRequest, flash);

		verify(linkMobilityMapperMock).toLinkMobilitySmsRequest(sendSmsRequest, flash);
		verify(linkMobilityClientMock).send(linkMobilitySmsRequest);
	}

	@Test
	void healthCheck() {

		linkMobilityIntegration.healthCheck();

		verify(linkMobilityClientMock).healthCheck();
	}

}
