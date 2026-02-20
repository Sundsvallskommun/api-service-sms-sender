package se.sundsvall.smssender.provider;

import org.jetbrains.annotations.NotNull;
import se.sundsvall.dept44.problem.Problem;
import se.sundsvall.smssender.api.model.SendSmsRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public interface SmsProvider extends Comparable<SmsProvider> {

	boolean sendSms(SendSmsRequest request, boolean flash);

	boolean isEnabled();

	boolean isFlashSmsCapable();

	String getName();

	int getPriority();

	default void verifyFlashCapability(final boolean flash) {
		if (flash && !isFlashSmsCapable()) {
			throw Problem.valueOf(BAD_REQUEST, String.format("SMS provider %s does not support flash messages", getName()));
		}
	}

	@Override
	default int compareTo(@NotNull final SmsProvider other) {
		return Integer.compare(getPriority(), other.getPriority());
	}
}
