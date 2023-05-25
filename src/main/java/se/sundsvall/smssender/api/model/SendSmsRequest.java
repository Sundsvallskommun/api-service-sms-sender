package se.sundsvall.smssender.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import se.sundsvall.dept44.common.validators.annotation.ValidMobileNumber;
import se.sundsvall.smssender.model.Priority;

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
    @Schema(description = "Mobile number", requiredMode = REQUIRED, example = "0701234567")
    @ValidMobileNumber
    private String mobileNumber;

    @Schema(description = "Priority", hidden = true)
    private Priority priority;
    
	@NotBlank
    private String message;
}
