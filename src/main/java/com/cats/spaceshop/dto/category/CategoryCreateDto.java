package com.cats.spaceshop.dto.category;

import com.cats.spaceshop.common.ExtendedValidation;
import com.cats.spaceshop.dto.validation.CosmicWordCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ CategoryCreateDto.class, ExtendedValidation.class })
public class CategoryCreateDto {

    @Schema(description = "The name of the category. Must contain a cosmic term such as 'star', 'galaxy', or 'comet'.",
            example = "Galactic Exploration",
            maxLength = 100)
    @NotBlank(message = "Category name is mandatory")
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    @CosmicWordCheck
    String name;

    @Schema(description = "A brief description of the category",
            example = "Products related to space exploration",
            maxLength = 255)
    @Size(max = 255, message = "Category description cannot exceed 255 characters")
    String description;
}
