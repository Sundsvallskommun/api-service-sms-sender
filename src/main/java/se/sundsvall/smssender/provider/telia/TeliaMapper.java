package se.sundsvall.smssender.provider.telia;

import java.util.Optional;

import org.springframework.stereotype.Component;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.model.Priority;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;

@Component
class TeliaMapper {

    SmsServiceRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest) {
        return new SmsServiceRequest()
            .originator(smsRequest.getSender().getName())
            .destinationNumber(smsRequest.getMobileNumber())
            .deliveryPriority(Optional.ofNullable(smsRequest.getPriority())
                .map(Priority::toString)
                .map(String::toLowerCase)
                .orElse(null))
            .message(smsRequest.getMessage());
    }
}
