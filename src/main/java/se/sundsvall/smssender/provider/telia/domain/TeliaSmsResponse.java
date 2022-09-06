package se.sundsvall.smssender.provider.telia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeliaSmsResponse {

    private String statusCode;
    private String statusMessage;

    public boolean isSent() {
        return "0".equals(statusCode) && "ok".equals(statusMessage);
    }
}
