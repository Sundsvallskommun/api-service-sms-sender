package se.sundsvall.smssender.api;

import static se.sundsvall.smssender.model.TypeOfNumber.MSISDN;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import se.sundsvall.smssender.api.model.SendFlashSmsRequest;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.api.model.SendSmsResponse;
import se.sundsvall.smssender.provider.SmsProviderRouter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "SMS Resources")
class SmsResource {

    private final SmsProviderRouter smsProviderRouter;

    SmsResource(final SmsProviderRouter smsProviderRouter) {
        this.smsProviderRouter = smsProviderRouter;
    }

    @Operation(summary = "Send an SMS")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successful Operation",
            content = @Content(schema = @Schema(implementation = SendSmsResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = Problem.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = Problem.class))
        )
    })
    @PostMapping("/send/sms")
    ResponseEntity<SendSmsResponse> sendSms(@Valid @RequestBody final SendSmsRequest request) {
        var sent = smsProviderRouter.sendSms(request);

        return ResponseEntity.ok(SendSmsResponse.builder()
            .withSent(sent)
            .build());
    }

    @Operation(hidden = true)
    @PostMapping(value = "/send/sms", params = "flash=true")
    ResponseEntity<SendSmsResponse> sendFlashSms(@Valid @RequestBody final SendFlashSmsRequest request) {
        // Replace hyphens and spaces, if any
        var mobileNumber = request.getMobileNumber()
            .replace("-", "")
            .replace(" ", "");

        // Remap the flash SMS request to a regular SMS request
        var mappedRequest = SendSmsRequest.builder()
            .withSender(request.getSender())
            .withMobileNumber(mobileNumber)
            .withMessage(request.getMessage())
            .build();

        // We assume that the submitted number is a "short number", but if it starts with a "+" char,
        // we re-map the number type to MSISDN
        if (mobileNumber.startsWith("+")) {
            mappedRequest = mappedRequest.withTypeOfNumber(MSISDN);
        }

        var sent = smsProviderRouter.sendFlashSms(mappedRequest);

        return ResponseEntity.ok(SendSmsResponse.builder()
            .withSent(sent)
            .build());
    }
}
