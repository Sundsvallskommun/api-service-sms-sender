package se.sundsvall.smssender.provider.telia;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import se.sundsvall.smssender.provider.telia.domain.TeliaSmsRequest;
import se.sundsvall.smssender.provider.telia.domain.TeliaSmsResponse;

@FeignClient(
    name = TeliaSmsProvider.PROVIDER_NAME,
    url = "${provider.telia.base-url}",
    configuration = TeliaSmsProviderConfiguration.class
)
interface TeliaClient {

    @PostMapping("/sendSms")
    TeliaSmsResponse send(TeliaSmsRequest request);
}
