package se.sundsvall.smssender.exception;

import java.io.Serial;

public class SmsException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1443336860612625661L;

	public SmsException(final String message) {
		super(message);
	}

	public SmsException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
