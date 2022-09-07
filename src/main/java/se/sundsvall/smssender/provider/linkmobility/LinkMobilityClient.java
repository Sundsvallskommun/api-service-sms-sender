package se.sundsvall.smssender.provider.linkmobility;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsRequest;
import se.sundsvall.smssender.provider.linkmobility.domain.LinkMobilitySmsResponse;

@FeignClient(
    name = LinkMobilitySmsProvider.PROVIDER_NAME,
    url = "${provider.linkmobility.base-url}",
    configuration = LinkMobilitySmsProviderConfiguration.class
)
interface LinkMobilityClient {

    @PostMapping("/sms/send")
    LinkMobilitySmsResponse send(LinkMobilitySmsRequest request);
}
