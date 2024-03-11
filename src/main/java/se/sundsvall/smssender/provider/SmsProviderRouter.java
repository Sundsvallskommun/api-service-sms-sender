package se.sundsvall.smssender.provider;

import static java.lang.String.format;
import static org.zalando.problem.Status.BAD_GATEWAY;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.exception.SmsException;

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
        final var filteredSmsProviderQueue = new PriorityQueue<SmsProvider>();
        filteredSmsProviderQueue.addAll(smsProviders.stream()
            .filter(filter)
            .toList());

        return filteredSmsProviderQueue;
    }

    public boolean sendSms(final SendSmsRequest request) {
        return sendSmsUsingProviderQueue(new PriorityQueue<>(smsProviderQueue), request, false);
    }

    public boolean sendFlashSms(final SendSmsRequest request) {
        return sendSmsUsingProviderQueue(new PriorityQueue<>(flashSmsProviderQueue), request, true);
    }

    boolean sendSmsUsingProviderQueue(final Queue<SmsProvider> providerQueue,
            final SendSmsRequest request, final boolean flash) {
        final var currentProvider = providerQueue.poll();
        final var nextProvider = providerQueue.peek();
        final var smsType = getSmsType(flash);

        // This should never happen - only to avoid IDE possible-null-warnings...
        if (currentProvider == null) {
            throw Problem.valueOf(BAD_GATEWAY, format("No remaining %s providers available", smsType));
        }

        // Try the current provider
        return retryTemplate.execute(context -> {
            LOG.info("Attempting to send {} using {}", smsType, currentProvider.getName());

            // Wrap the send-call in a try-catch and rethrow any exception, since the response and
            // exception is otherwise suppressed by the retry mechanism
            try {
                return currentProvider.sendSms(request, flash);
            } catch (final Exception e) {
                final var message = format("Unable to send %s using %s", smsType, currentProvider.getName());

                throw new SmsException(message, e);
            }
        }, recoveryContext -> {
            LOG.info("Unable to send {} using {}", smsType, currentProvider.getName());

            // We don't have a "next" provider - we're out of sending options
            if (nextProvider == null) {
                final var message = format("Unable to send %s using any provider", smsType);
                LOG.warn(message);

                throw Problem.valueOf(BAD_GATEWAY, message);
            }

            // Try the next provider
            return sendSmsUsingProviderQueue(providerQueue, request, flash);
        });
    }

    String getSmsType(final boolean flash) {
        return flash ? "FLASH SMS" : "SMS";
    }
}
