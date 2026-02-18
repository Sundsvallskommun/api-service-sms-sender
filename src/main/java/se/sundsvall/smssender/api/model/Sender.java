package se.sundsvall.smssender.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class Sender {

	@Schema(description = "The sender of the SMS, swedish letters(å,ä,ö) will be replaced by (a,a,o) respectively",
		requiredMode = REQUIRED,
		maxLength = 11,
		minLength = 3,
		examples = "sender")
	@Size(max = 11, min = 3)
	@NotBlank
	private String name;
}
