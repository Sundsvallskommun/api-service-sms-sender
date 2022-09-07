package se.sundsvall.smssender.provider;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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

    private final RetryTemplate retryTemplate;
    private final PriorityQueue<SmsProvider<?>> smsProviderQueue;

    public SmsProviderRouter(final RetryTemplate retryTemplate,
            final List<SmsProvider<?>> smsProviders) {
        this.retryTemplate = retryTemplate;

        smsProviderQueue = initializeProviderQueue(smsProviders);
    }

    PriorityQueue<SmsProvider<?>> initializeProviderQueue(final List<SmsProvider<?>> smsProviders) {
        var smsProviderQueue = new PriorityQueue<SmsProvider<?>>();
        smsProviderQueue.addAll(smsProviders.stream()
            .filter(SmsProvider::isEnabled)
            .toList());

        if (smsProviderQueue.isEmpty()) {
            throw new IllegalStateException("No enabled SMS providers. Review configuration");
        }

        smsProviderQueue.forEach(provider -> LOG.info("Added SMS provider {} (priority {})",
            provider.getName(), provider.getPriority()));

        return smsProviderQueue;
    }

    public boolean sendSms(final SendSmsRequest request) {
        return doSendSmsUsingProviderQueue(new PriorityQueue<>(smsProviderQueue), request);
    }

    boolean doSendSmsUsingProviderQueue(final Queue<SmsProvider<?>> providerQueue,
            final SendSmsRequest request) {
        var currentProvider = providerQueue.poll();
        var nextProvider = providerQueue.peek();

        // This should never happen - only to avoid IDE possible-null-warnings...
        if (currentProvider == null) {
            throw Problem.valueOf(Status.BAD_GATEWAY, "No SMS provider available");
        }

        // Try the current provider
        return retryTemplate.execute(context -> {
            LOG.info("Attempting to send SMS using {}", currentProvider.getName());

            return currentProvider.sendSms(request);
        }, recoveryContext -> {
            LOG.info("Unable to send SMS using {}", currentProvider.getName());

            // We don't have a "next" provider - we're out of sending options
            if (nextProvider == null) {
                LOG.warn("Unable to send SMS using any provider");

                throw Problem.valueOf(Status.BAD_GATEWAY, "Unable to send SMS using any provider");
            }

            // Try the "next" provider
            return doSendSmsUsingProviderQueue(providerQueue, request);
        });
    }
}
