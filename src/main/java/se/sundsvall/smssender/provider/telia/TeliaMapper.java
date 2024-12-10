package se.sundsvall.smssender.provider.telia;

import static java.util.Optional.ofNullable;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;
import org.springframework.stereotype.Component;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.model.Priority;

@Component
class TeliaMapper {

	SmsServiceRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest, final boolean flash) {
		return new SmsServiceRequest()
			.originator(smsRequest.getSender().getName())
			.destinationNumber(smsRequest.getMobileNumber())
			.deliveryPriority(ofNullable(smsRequest.getPriority())
				.map(Priority::toString)
				.map(String::toLowerCase)
				.orElse(null))
			.flashSms(flash ? true : null)
			.message(smsRequest.getMessage());
	}
}
