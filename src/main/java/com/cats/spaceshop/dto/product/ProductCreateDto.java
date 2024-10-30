package com.cats.spaceshop.dto.product;

import com.cats.spaceshop.common.ExtendedValidation;
import com.cats.spaceshop.dto.validation.CosmicWordCheck;
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

    @NotBlank(message = "Product name is mandatory")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    @CosmicWordCheck
    String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @NotNull(message = "Category ID is mandatory")
    String categoryId;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    BigDecimal price;

    @NotNull(message = "Stock quantity is mandatory")
    @DecimalMin(value = "0", message = "Stock quantity must be zero or positive")
    Integer stockQuantity;

    @Pattern(regexp = "^(\\w|\\s|-|_)+$", message = "Product SKU must be alphanumeric and may contain spaces, dashes, and underscores")
    String sku;
}
