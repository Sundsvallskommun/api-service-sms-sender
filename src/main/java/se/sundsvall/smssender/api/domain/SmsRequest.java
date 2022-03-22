package se.sundsvall.smssender.api.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
@Getter
@Setter
public class SmsRequest {

    @NotBlank
    @Size(max = 11)
    @Schema(required = true, description = "The Sender of the SMS", maxLength = 11, example = "sender")
    private String sender;
    
	@NotBlank
    @Schema(required = true, description = "Mobile number should start with +467x")
    @Pattern(regexp = "^\\+467[02369]\\d{7}$")
    private String mobileNumber;
    
	@NotBlank
    private String message;
}
