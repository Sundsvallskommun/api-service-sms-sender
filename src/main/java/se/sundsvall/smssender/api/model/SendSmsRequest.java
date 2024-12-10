package se.sundsvall.smssender.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.sundsvall.dept44.common.validators.annotation.ValidMSISDN;
import se.sundsvall.smssender.model.Priority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SendSmsRequest {

	@Valid
	@NotNull
	private Sender sender;

	@Schema(description = "Mobile number incl. country code", requiredMode = REQUIRED, example = "+46701234567")
	@ValidMSISDN
	private String mobileNumber;

	@Schema(
		description = "Priority - indicates whether the SMS may be sent between 20:00 and 08:00 or not",
		defaultValue = "NORMAL")
	private Priority priority;

	@NotBlank
	private String message;
}
