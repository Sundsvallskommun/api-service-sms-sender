package se.sundsvall.smssender.provider;

import org.jetbrains.annotations.NotNull;

import se.sundsvall.smssender.api.model.SendSmsRequest;

public interface SmsProvider<REQUEST> extends Comparable<SmsProvider<?>> {

    boolean sendSms(SendSmsRequest request);

    REQUEST mapFromSmsRequest(SendSmsRequest smsRequest);

    boolean isEnabled();

    String getName();

    int getPriority();

    @Override
    default int compareTo(@NotNull final SmsProvider<?> other) {
        return Integer.compare(getPriority(), other.getPriority());
    }
}
