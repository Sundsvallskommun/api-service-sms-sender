package se.sundsvall.smssender.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
public class Sender {

    @Schema(description = "The sender of the SMS, must be between 3-11 characters (allowed characters: a-z, A-Z, 0-9, whitespace) and start with a non-numeric character",
        requiredMode = REQUIRED, maxLength = 11, minLength = 3, example = "sender")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ]{2,10}$", message = "sender must be between 3-11 characters (allowed characters: a-z, A-Z, 0-9, whitespace) and start with a non-numeric character")
    @NotBlank
    private String name;
}
