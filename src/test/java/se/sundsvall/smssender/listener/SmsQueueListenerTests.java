package se.sundsvall.smssender.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class SmsQueueListenerTests {

	private final SmsQueueListener listener = new SmsQueueListener();

	@Test
	void receiveLogsPayload(final CapturedOutput output) {
		listener.receive("someMessage");

		assertThat(output).contains("Received AMQP message: someMessage");
	}

	/**
	 * The AMQP user has no {@code configure} permission, so the listener must bind to a
	 * pre-existing queue. {@code queuesToDeclare} or {@code bindings} would make the
	 * container declare topology at startup and get 403 ACCESS_REFUSED.
	 */
	@Test
	void listenerBindsToPreExistingQueueOnly() throws NoSuchMethodException {
		final var annotation = SmsQueueListener.class
			.getDeclaredMethod("receive", String.class)
			.getAnnotation(RabbitListener.class);

		assertThat(annotation).isNotNull();
		assertThat(annotation.queues()).containsExactly("api-fabriken.sms-sender.sms");
		assertThat(annotation.queuesToDeclare()).isEmpty();
		assertThat(annotation.bindings()).isEmpty();
	}
}
