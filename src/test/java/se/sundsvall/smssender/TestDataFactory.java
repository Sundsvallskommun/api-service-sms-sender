package se.sundsvall.smssender;

import java.util.function.Consumer;
import se.sundsvall.smssender.api.model.SendFlashSmsRequest;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;

public final class TestDataFactory {

	private TestDataFactory() {}

	public static SendSmsRequest createValidSendSmsRequest() {
		return createValidSendSmsRequest(null);
	}

	public static SendSmsRequest createValidSendSmsRequest(final Consumer<SendSmsRequest> modifier) {
		final var request = SendSmsRequest.builder()
			.withSender(Sender.builder()
				.withName("sender")
				.build())
			.withMessage("message")
			.withMobileNumber("+46701740605")
			.build();

		if (modifier != null) {
			modifier.accept(request);
		}

		return request;
	}

	public static SendFlashSmsRequest createValidSendFlashSmsRequest() {
		return createValidSendFlashSmsRequest(null);
	}

	public static SendFlashSmsRequest createValidSendFlashSmsRequest(final Consumer<SendFlashSmsRequest> modifier) {
		final var request = SendFlashSmsRequest.builder()
			.withSender(Sender.builder()
				.withName("sender")
				.build())
			.withMessage("message")
			.withMobileNumber("+46701740605")
			.build();

		if (modifier != null) {
			modifier.accept(request);
		}

		return request;
	}

	public static Sender createValidSender() {
		return createValidSender(null);
	}

	public static Sender createValidSender(final Consumer<Sender> modifier) {
		final var sender = Sender.builder()
			.withName("sender")
			.build();

		if (modifier != null) {
			modifier.accept(sender);
		}
		return sender;
	}

}
