package se.sundsvall.smssender.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.provider.SmsProviderRouter.IS_ENABLED;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class SmsProviderRouterTests {

    @Mock
    private RetryTemplate mockRetryTemplate;
    @Mock
    private SmsProvider mockSmsProvider1;
    @Mock
    private SmsProvider mockSmsProvider2;

    private SmsProviderRouter router;

    @BeforeEach
    void setUp() {
        when(mockSmsProvider1.getName()).thenReturn("P1.default");
        when(mockSmsProvider1.isEnabled()).thenReturn(true);
        when(mockSmsProvider1.isFlashSmsCapable()).thenReturn(true);

        when(mockSmsProvider2.getName()).thenReturn("P2.default");
        when(mockSmsProvider2.isEnabled()).thenReturn(true);
        when(mockSmsProvider2.isFlashSmsCapable()).thenReturn(true);

        router = new SmsProviderRouter(mockRetryTemplate, List.of(mockSmsProvider1, mockSmsProvider2));
    }

    @Test
    void testInitializeProviderQueue() {
        when(mockSmsProvider1.getName()).thenReturn("P1");
        when(mockSmsProvider2.getName()).thenReturn("P2");

        final var smsProviderQueue = router.initializeProviderQueue(
            List.of(mockSmsProvider1, mockSmsProvider2), IS_ENABLED);

        assertThat(smsProviderQueue).hasSize(2);
        assertThat(smsProviderQueue.poll()).satisfies(provider -> {
            assertThat(provider).isNotNull();
            assertThat(provider.getName()).isEqualTo(mockSmsProvider1.getName());
        });
        assertThat(smsProviderQueue.poll()).satisfies(provider -> {
            assertThat(provider).isNotNull();
            assertThat(provider.getName()).isEqualTo(mockSmsProvider2.getName());
        });
    }

    @Test
    void testInitializeProviderQueueWithOneDisabledProvider() {
        when(mockSmsProvider1.isEnabled()).thenReturn(false);

        final var smsProviderQueue = router.initializeProviderQueue(
            List.of(mockSmsProvider1, mockSmsProvider2), IS_ENABLED);

        assertThat(smsProviderQueue).hasSize(1);
        assertThat(smsProviderQueue.poll()).satisfies(provider -> {
            assertThat(provider).isNotNull();
            assertThat(provider.getName()).isEqualTo(mockSmsProvider2.getName());
        });
    }

    @Test
    void testInitializeProviderQueueWithAllProvidersDisabled() {
        when(mockSmsProvider1.isEnabled()).thenReturn(false);
        when(mockSmsProvider2.isEnabled()).thenReturn(false);

        final var smsProviderQueue = router.initializeProviderQueue(
            List.of(mockSmsProvider1, mockSmsProvider2), IS_ENABLED);

        assertThat(smsProviderQueue).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testGetSmsType(final boolean flash) {
        if (flash) {
            assertThat(router.getSmsType(flash)).isEqualTo("FLASH SMS");
        } else {
            assertThat(router.getSmsType(flash)).isEqualTo("SMS");
        }
    }
}
