package se.sundsvall.smssender.provider;

import se.sundsvall.smssender.api.model.SendSmsRequest;

public interface SmsProvider<REQUEST> {

    boolean sendSms(SendSmsRequest request);

    REQUEST mapFromSmsRequest(SendSmsRequest smsRequest);
}
