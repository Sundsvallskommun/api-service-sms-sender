package se.sundsvall.smssender.provider;

import org.jetbrains.annotations.NotNull;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.smssender.api.model.SendSmsRequest;

public interface SmsProvider extends Comparable<SmsProvider> {

    boolean sendSms(SendSmsRequest request, boolean flash);

    boolean isEnabled();

    boolean isFlashSmsCapable();

    String getName();

    int getPriority();

    default void verifyFlashCapability(final boolean flash) {
        if (flash && !isFlashSmsCapable()) {
            throw Problem.valueOf(Status.BAD_REQUEST, String.format("SMS provider %s does not support flash messages", getName()));
        }
    }

    @Override
    default int compareTo(@NotNull final SmsProvider other) {
        return Integer.compare(getPriority(), other.getPriority());
    }
}
