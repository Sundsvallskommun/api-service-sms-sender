package se.sundsvall.smssender.provider;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.smssender.api.model.SendSmsRequest;

import lombok.Generated;

@Component
// @Generated for now, since I don't have the energy to figure out how to mock the RetryTemplate stuff...
@Generated
public class SmsProviderRouter {

    private static final Logger LOG = LoggerFactory.getLogger(SmsProviderRouter.class);

    static final Predicate<SmsProvider> IS_ENABLED = SmsProvider::isEnabled;
    static final Predicate<SmsProvider> IS_FLASH_SMS_CAPABLE = IS_ENABLED.and(SmsProvider::isFlashSmsCapable);

    private final RetryTemplate retryTemplate;
    private final PriorityQueue<SmsProvider> smsProviderQueue;
    private final PriorityQueue<SmsProvider> flashSmsProviderQueue;

    public SmsProviderRouter(final RetryTemplate retryTemplate, final List<SmsProvider> smsProviders) {
        this.retryTemplate = retryTemplate;

        // Initialize regular SMS provider queue
        smsProviderQueue = initializeProviderQueue(smsProviders, IS_ENABLED);
        if (smsProviderQueue.isEmpty()) {
            throw new IllegalStateException("No enabled SMS providers. Review configuration");
        }
        smsProviderQueue.forEach(provider -> LOG.info("Added SMS provider {}", provider.getName()));

        // Initialize flash-SMS capable provider queue
        flashSmsProviderQueue = initializeProviderQueue(smsProviders, IS_FLASH_SMS_CAPABLE);
        if (flashSmsProviderQueue.isEmpty()) {
            throw new IllegalStateException("No enabled flash-SMS-capable providers. Review configuration");
        }
        flashSmsProviderQueue.forEach(provider -> LOG.info("Added flash-SMS capable provider {}",
            provider.getName()));
    }

    PriorityQueue<SmsProvider> initializeProviderQueue(final List<SmsProvider> smsProviders,
            final Predicate<SmsProvider> filter) {
        var smsProviderQueue = new PriorityQueue<SmsProvider>();
        smsProviderQueue.addAll(smsProviders.stream()
            .filter(filter)
            .toList());

        return smsProviderQueue;
    }

    public boolean sendSms(final SendSmsRequest request) {
        return sendSmsUsingProviderQueue(new PriorityQueue<>(smsProviderQueue), request, false);
    }

    public boolean sendFlashSms(final SendSmsRequest request) {
        return sendSmsUsingProviderQueue(new PriorityQueue<>(flashSmsProviderQueue), request, true);
    }

    boolean sendSmsUsingProviderQueue(final Queue<SmsProvider> providerQueue,
            final SendSmsRequest request, final boolean flash) {
        var currentProvider = providerQueue.poll();
        var nextProvider = providerQueue.peek();

        // This should never happen - only to avoid IDE possible-null-warnings...
        if (currentProvider == null) {
            throw Problem.valueOf(Status.BAD_GATEWAY, String.format("No remaining %sSMS providers available", (flash ? "FLASH " : "")));
        }

        // Try the current provider
        return retryTemplate.execute(context -> {
            LOG.info("Attempting to send {}SMS using {}", (flash ? "FLASH " : ""), currentProvider.getName());

            // Wrap the send-call in a try-catch and rethrow any exception, since the response and
            // exception is otherwise suppressed by the retry mechanism
            try {
                return currentProvider.sendSms(request, flash);
            } catch (Exception e) {
                LOG.warn(String.format("Unable to send %sSMS using %s", (flash ? "FLASH " : ""), currentProvider.getName()), e);

                throw e;
            }
        }, recoveryContext -> {
            LOG.info("Unable to send SMS using {}", currentProvider.getName());

            // We don't have a "next" provider - we're out of sending options
            if (nextProvider == null) {
                var message = String.format("Unable to send %sSMS using any provider", (flash ? "FLASH " : ""));
                LOG.warn(message);

                throw Problem.valueOf(Status.BAD_GATEWAY, message);
            }

            // Try the next provider
            return sendSmsUsingProviderQueue(providerQueue, request, flash);
        });
    }
}
