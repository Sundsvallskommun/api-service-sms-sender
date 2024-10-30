package se.sundsvall.smssender.provider.linkmobility.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
class LinkMobilitySmsRequestTests {

	@Test
	void testBuilderAndGetters() {
		final var request = LinkMobilitySmsRequest.builder()
			.withPlatformId("platform id")
			.withPlatformPartnerId("platform partner")
			.withUseDeliveryReport(true)
			.withSourceTON("NUMERIC")
			.withSource("source")
			.withDestinationTON("MSID")
			.withDestination("+46701234567")
			.withUserData("message")
			.build();

		assertThat(request.getPlatformId()).isEqualTo("platform id");
		assertThat(request.getPlatformPartnerId()).isEqualTo("platform partner");
		assertThat(request.isUseDeliveryReport()).isTrue();
		assertThat(request.getSourceTON()).isEqualTo("NUMERIC");
		assertThat(request.getSource()).isEqualTo("source");
		assertThat(request.getDestinationTON()).isEqualTo("MSID");
		assertThat(request.getDestination()).isEqualTo("+46701234567");
		assertThat(request.getUserData()).isEqualTo("message");

		// just for coverage
		assertThat(request.toString()).isNotNull();
	}
}
