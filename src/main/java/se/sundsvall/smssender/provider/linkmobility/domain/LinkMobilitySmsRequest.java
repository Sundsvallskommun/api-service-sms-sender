package se.sundsvall.smssender.provider.linkmobility.domain;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class LinkMobilitySmsRequest {

	private String platformId;
	private String platformPartnerId;
	@Builder.Default
	private boolean useDeliveryReport = false;
	@Builder.Default
	private String sourceTON = "ALPHANUMERIC";
	private String source;
	@Builder.Default
	private String destinationTON = "MSISDN";
	private String destination;
	private String userData;
	private String priority;

	@With
	private Map<String, String> customParameters;
}
