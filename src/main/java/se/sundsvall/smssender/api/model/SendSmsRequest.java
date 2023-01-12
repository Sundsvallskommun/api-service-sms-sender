package se.sundsvall.smssender.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import se.sundsvall.dept44.common.validators.annotation.ValidMobileNumber;
import se.sundsvall.smssender.model.TypeOfNumber;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

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
    
	@NotBlank
    private String message;

    @With
    @Builder.Default
    @JsonIgnore
    private TypeOfNumber typeOfNumber = TypeOfNumber.SHORTNUMBER;
}
