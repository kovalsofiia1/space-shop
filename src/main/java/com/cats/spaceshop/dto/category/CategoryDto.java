package com.cats.spaceshop.dto.category;

import com.cats.spaceshop.common.ExtendedValidation;
import com.cats.spaceshop.dto.validation.CosmicWordCheck;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ CategoryDto.class, ExtendedValidation.class })
public class CategoryDto {

    @NotBlank(message = "Category ID is mandatory")
    String categoryId;

    @NotBlank(message = "Category name is mandatory")
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    @CosmicWordCheck
    String name;

    @Size(max = 255, message = "Category description cannot exceed 255 characters")
    String description;

}