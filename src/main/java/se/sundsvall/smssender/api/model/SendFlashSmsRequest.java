package se.sundsvall.smssender.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Custom send-SMS-request that doesn't validate that the mobile number is on the regular Swedish
 * format ^07[02369]\d{7}$ .
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
@Schema(hidden = true)
public class SendFlashSmsRequest {

    @Valid
    @NotNull
    private Sender sender;

	@NotBlank
    @Schema(description = "Mobile number", requiredMode = REQUIRED, example = "0701234567")
    private String mobileNumber;
    
	@NotBlank
    private String message;
}
