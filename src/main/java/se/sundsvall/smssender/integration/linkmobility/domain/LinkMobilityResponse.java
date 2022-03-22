package se.sundsvall.smssender.integration.linkmobility.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkMobilityResponse {

    @JsonProperty("resultCode")
    private ResponseStatus status;
}
