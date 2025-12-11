package se.sundsvall.smssender.provider.linkmobility;

import org.springframework.stereotype.Component;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;

@Component
public class LinkMobilityIntegration {

	private final LinkMobilityClient linkMobilityClient;
	private final LinkMobilityMapper linkMobilityMapper;

	public LinkMobilityIntegration(final LinkMobilityClient linkMobilityClient,
		final LinkMobilityMapper linkMobilityMapper) {
		this.linkMobilityClient = linkMobilityClient;
		this.linkMobilityMapper = linkMobilityMapper;
	}

	public LinkMobilitySmsResponse sendSms(final SendSmsRequest request, final boolean flash) {
		var linkMobilitySmsRequest = linkMobilityMapper.toLinkMobilitySmsRequest(request, flash);
		return linkMobilityClient.send(linkMobilitySmsRequest);
	}

	public void healthCheck() {
		linkMobilityClient.healthCheck();
	}

}
