package se.sundsvall.smssender;

import java.util.function.Consumer;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;

public final class TestDataFactory {

    private TestDataFactory() { }

    public static SendSmsRequest createValidSendSmsRequest() {
        return createValidSendSmsRequest(null);
    }

    public static SendSmsRequest createValidSendSmsRequest(final Consumer<SendSmsRequest> modifier) {
        var request = SendSmsRequest.builder()
            .withSender(Sender.builder()
                .withName("sender")
                .build())
            .withMessage("message")
            .withMobileNumber("0701234567")
            .build();

        if (modifier != null) {
            modifier.accept(request);
        }

        return request;
    }
}
