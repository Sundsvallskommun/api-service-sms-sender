package se.sundsvall.smssender.provider;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.smssender.provider.linkmobility.LinkMobilitySmsProvider;
import se.sundsvall.smssender.provider.telia.TeliaSmsProvider;

@ExtendWith(MockitoExtension.class)
class SmsProviderRouterTests {

    @Mock
    private TeliaSmsProvider mockTeliaSmsProvider;
    @Mock
    private LinkMobilitySmsProvider mockLinkMobilitySmsProvider;

    @InjectMocks
    private SmsProviderRouter router;

    @Test
    void testSendSms() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(() -> router.sendSms(null));
    }

    @Test
    void testSendSmsUsingTelia() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(() -> router.sendSmsUsingTelia(null));
    }

    @Test
    void testSendSmsUsingLinkMobility() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(() -> router.sendSmsUsingLinkMobility(null));
    }
}
