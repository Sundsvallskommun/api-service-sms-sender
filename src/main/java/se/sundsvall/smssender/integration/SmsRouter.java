package se.sundsvall.smssender.integration;

import org.springframework.stereotype.Component;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.integration.linkmobility.LinkMobilitySmsProvider;
import se.sundsvall.smssender.integration.telia.TeliaSmsProvider;

@Component
public class SmsRouter {

    private final TeliaSmsProvider teliaSmsProvider;
    private final LinkMobilitySmsProvider linkMobilitySmsProvider;

    SmsRouter(final TeliaSmsProvider teliaSmsProvider, final LinkMobilitySmsProvider linkMobilitySmsProvider) {
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
