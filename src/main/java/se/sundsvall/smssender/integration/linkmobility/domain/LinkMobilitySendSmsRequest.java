package se.sundsvall.smssender.integration.linkmobility.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class LinkMobilitySendSmsRequest {

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
}
