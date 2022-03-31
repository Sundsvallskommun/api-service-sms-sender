package se.sundsvall.smssender.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SendSmsRequest {

    @Valid
    @NotNull
    private Sender sender;

	@NotBlank
    @Schema(required = true, description = "Mobile number should start with +467x")
    @Pattern(regexp = "^\\+467[02369]\\d{7}$")
    private String mobileNumber;
    
	@NotBlank
    private String message;
}
