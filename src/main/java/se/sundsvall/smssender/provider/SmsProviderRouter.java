package se.sundsvall.smssender.provider;

import org.springframework.stereotype.Component;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.linkmobility.LinkMobilitySmsProvider;
import se.sundsvall.smssender.provider.telia.TeliaSmsProvider;

@Component
public class SmsProviderRouter {

    private final TeliaSmsProvider teliaSmsProvider;
    private final LinkMobilitySmsProvider linkMobilitySmsProvider;

    SmsProviderRouter(final TeliaSmsProvider teliaSmsProvider, final LinkMobilitySmsProvider linkMobilitySmsProvider) {
        this.teliaSmsProvider = teliaSmsProvider;
        this.linkMobilitySmsProvider = linkMobilitySmsProvider;
    }

    // TODO: circuitbreaker + failover from Telia to LinkMobility

    public boolean sendSms(final SendSmsRequest request) {
        throw new UnsupportedOperationException("Not implemented yet...");
    }

    public boolean sendSmsUsingTelia(final SendSmsRequest request) {
        throw new UnsupportedOperationException("Not implemented yet...");
    }

    public boolean sendSmsUsingLinkMobility(final SendSmsRequest request) {
        throw new UnsupportedOperationException("Not implemented yet...");
    }
}
