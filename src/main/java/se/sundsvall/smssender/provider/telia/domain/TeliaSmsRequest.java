package se.sundsvall.smssender.provider.telia.domain;

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
public class TeliaSmsRequest {

    @Builder.Default
    private String originator = "Telia";
    private String destinationNumber;
    private String message;
    @Builder.Default
    private String deliveryPriority = "high";
}
