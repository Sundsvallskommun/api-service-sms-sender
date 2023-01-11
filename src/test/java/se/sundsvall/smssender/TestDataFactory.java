package se.sundsvall.smssender;

import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.Sender;

public final class TestDataFactory {

    private TestDataFactory() { }

    public static SendSmsRequest createValidSendSmsRequest() {
        return SendSmsRequest.builder()
            .withSender(Sender.builder()
                .withName("sender")
                .build())
            .withMessage("message")
            .withMobileNumber("0701234567")
            .build();
    }
}
