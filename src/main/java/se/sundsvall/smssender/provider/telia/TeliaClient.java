package se.sundsvall.smssender.provider.telia;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;

@FeignClient(
	name = TeliaSmsProvider.PROVIDER_NAME,
	url = "${provider.telia.base-url}",
	configuration = TeliaSmsProviderConfiguration.class)
interface TeliaClient {

	@PostMapping("/sendSms")
	ResponseEntity<Void> send(SmsServiceRequest request);
}
