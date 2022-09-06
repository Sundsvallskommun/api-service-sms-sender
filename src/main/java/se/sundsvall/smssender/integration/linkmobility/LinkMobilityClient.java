package se.sundsvall.smssender.integration.linkmobility;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilityResponse;
import se.sundsvall.smssender.integration.linkmobility.domain.LinkMobilitySendSmsRequest;

@FeignClient(
    name = LinkMobilitySmsProvider.PROVIDER_NAME,
    url = "${provider.linkmobility.base-url}",
    configuration = LinkMobilityConfiguration.class
)
interface LinkMobilityClient {

    @PostMapping("/sms/send")
    LinkMobilityResponse send(LinkMobilitySendSmsRequest request);
}
