package se.sundsvall.smssender.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import se.sundsvall.smssender.api.domain.SmsRequest;
import se.sundsvall.smssender.integration.SmsProperties;
import se.sundsvall.smssender.integration.linkmobility.LinkMobilityService;
import se.sundsvall.smssender.integration.telia.TeliaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Sms Service Resources")
public class SmsController {

    private final TeliaService teliaService;
    private final LinkMobilityService linkMobilityService;

    private final SmsProperties smsProperties;

    public SmsController(TeliaService teliaService, LinkMobilityService linkMobilityService, SmsProperties smsProperties) {
        this.teliaService = teliaService;
        this.linkMobilityService = linkMobilityService;
        this.smsProperties = smsProperties;
    }

    @Operation(summary = "Send an SMS")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
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
    public ResponseEntity<Boolean> sendSms(@Valid @RequestBody SmsRequest sms) {
        boolean response = switch (smsProperties.getProvider()) {
            case TELIA -> teliaService.sendSms(sms);
            case LINKMOBILITY -> linkMobilityService.sendSms(sms);
        };
        return ResponseEntity.ok(response);
    }
}
