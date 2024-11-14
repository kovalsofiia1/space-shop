package com.cats.spaceshop.dto.cosmocat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CosmoCatDto {

    @NotNull(message = "CosmoCat ID is mandatory")
    UUID id;

    @Schema(description = "CosmoCat's name, must be non-blank and up to 50 characters.")
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    String name;

    @Schema(description = "CosmoCat's email address")
    @Email(message = "Email should be valid")
    String email;

    @Schema(description = "CosmoCat's phone number, must match format '(123) 456-7890'")
    @Pattern(regexp = "^\\(\\d{3}\\) \\d{3}-\\d{4}$", message = "Phone number must be in format (123) 456-7890")
    String phoneNumber;

    @Schema(description = "CosmoCat's address, must be non-blank and up to 200 characters.")
    @NotBlank(message = "Address is mandatory")
    @Size(max = 200, message = "Address cannot exceed 200 characters")
    String address;
}
