package com.cats.spaceshop.dto.product;

import com.cats.spaceshop.common.ExtendedValidation;
import com.cats.spaceshop.dto.validation.CosmicWordCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductCreateDto {

    @Schema(
            description = "Product name must contain a cosmic term like 'star', 'galaxy', 'comet', etc.",
            example = "Galaxy Star",
            maxLength = 100
    )
    @NotBlank(message = "Product name is mandatory")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    @CosmicWordCheck
    String name;

    @Schema(
            description = "Detailed description of the product.",
            example = "A cosmic product filled with stardust.",
            maxLength = 500
    )
    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @Schema(
            description = "Unique identifier of the category the product belongs to.",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @NotNull(message = "Category ID is mandatory")
    String categoryId;

    @Schema(
            description = "The price of the product, must be greater than zero.",
            example = "15.99",
            minimum = "0.01"
    )
    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    BigDecimal price;

    @Schema(
            description = "Available stock quantity, must be zero or positive.",
            example = "10",
            minimum = "0"
    )
    @NotNull(message = "Stock quantity is mandatory")
    @DecimalMin(value = "0", message = "Stock quantity must be zero or positive")
    Integer stockQuantity;

    @Schema(
            description = "Product SKU, must be alphanumeric and may contain spaces, dashes, and underscores.",
            example = "GALAXY-STAR-001",
            pattern = "^(\\w|\\s|-|_)+$"
    )
    @Pattern(regexp = "^(\\w|\\s|-|_)+$", message = "Product SKU must be alphanumeric and may contain spaces, dashes, and underscores")
    String sku;
}
