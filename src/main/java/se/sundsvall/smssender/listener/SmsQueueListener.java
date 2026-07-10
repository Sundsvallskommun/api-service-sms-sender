package se.sundsvall.smssender.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Consumes from a queue declared externally by the RabbitMQ Messaging Topology Operator.
 * The AMQP user has an empty {@code configure} permission, so nothing here may declare a
 * queue, exchange or binding - use {@code queues}, never {@code queuesToDeclare} or
 * {@code bindings}, and define no {@code Declarable} beans anywhere in the application.
 * <p>
 * Logs only. Must not reach the SMS dispatch path, since both providers send real text
 * messages in the test environment.
 * <p>
 * Inert unless {@code rabbitmq.listener.enabled=true}. Without the bean the connection
 * factory stays lazy and the application opens no AMQP connection at all.
 */
@Component
@ConditionalOnProperty(name = "rabbitmq.listener.enabled", havingValue = "true")
class SmsQueueListener {

	private static final Logger LOG = LoggerFactory.getLogger(SmsQueueListener.class);

	@RabbitListener(queues = "api-fabriken.sms-sender.sms")
	void receive(final String payload) {
		LOG.info("Received AMQP message: {}", payload);
	}
}
