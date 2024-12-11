package se.sundsvall.smssender.api;

import static org.springframework.http.ResponseEntity.ok;
import static se.sundsvall.smssender.api.util.RequestCleaner.cleanMobileNumber;
import static se.sundsvall.smssender.api.util.RequestCleaner.cleanSenderName;
import static se.sundsvall.smssender.model.Priority.HIGH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import se.sundsvall.dept44.common.validators.annotation.ValidMunicipalityId;
import se.sundsvall.smssender.api.model.SendFlashSmsRequest;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.SendSmsResponse;
import se.sundsvall.smssender.provider.SmsProviderRouter;

@RestController
@Tag(name = "SMS Resources")
@Validated
@RequestMapping("/{municipalityId}/send/sms")
class SmsResource {

	private final SmsProviderRouter smsProviderRouter;

	SmsResource(final SmsProviderRouter smsProviderRouter) {
		this.smsProviderRouter = smsProviderRouter;
	}

	@Operation(summary = "Send an SMS", responses = {
		@ApiResponse(
			responseCode = "200",
			description = "Successful Operation",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(
			responseCode = "500",
			description = "Internal Server Error",
			content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	@PostMapping()
	ResponseEntity<SendSmsResponse> sendSms(
		@Parameter(name = "municipalityId", description = "Municipality id", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@Valid @RequestBody final SendSmsRequest request) {
		final var cleanedRequest = SendSmsRequest.builder()
			.withPriority(request.getPriority())
			.withMessage(request.getMessage())
			.withMobileNumber(cleanMobileNumber(request.getMobileNumber()))
			.withSender(cleanSenderName(request.getSender()))
			.build();

		final var sent = smsProviderRouter.sendSms(cleanedRequest);

		return ok(SendSmsResponse.builder()
			.withSent(sent)
			.build());
	}

	@Operation(hidden = true)
	@PostMapping(params = "flash=true")
	ResponseEntity<SendSmsResponse> sendFlashSms(
		@Parameter(name = "municipalityId", description = "Municipality id", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@Valid @RequestBody final SendFlashSmsRequest request) {
		// Remap the flash SMS request to a regular SMS request and always use HIGH priority for
		// flash SMS:es
		final var mappedRequest = SendSmsRequest.builder()
			.withPriority(HIGH)
			.withSender(cleanSenderName(request.getSender()))
			.withMobileNumber(cleanMobileNumber(request.getMobileNumber()))
			.withMessage(request.getMessage())
			.build();

		final var sent = smsProviderRouter.sendFlashSms(mappedRequest);

		return ok(SendSmsResponse.builder()
			.withSent(sent)
			.build());
	}

}
