package se.sundsvall.smssender.integration.telia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeliaResponse {

    private String statusCode;
    private String statusMessage;
}
