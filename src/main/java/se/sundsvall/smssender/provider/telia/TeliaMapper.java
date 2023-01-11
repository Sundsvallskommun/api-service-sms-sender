package se.sundsvall.smssender.provider.telia;

import org.springframework.stereotype.Component;

import se.sundsvall.smssender.api.model.SendSmsRequest;

import generated.com.teliacompany.c2b.smssender.SmsServiceRequest;

@Component
class TeliaMapper {

    SmsServiceRequest mapFromSendSmsRequest(final SendSmsRequest smsRequest) {
        return new SmsServiceRequest()
            .originator(smsRequest.getSender().getName())
            .destinationNumber(smsRequest.getMobileNumber())
            .message(smsRequest.getMessage());
    }
}
