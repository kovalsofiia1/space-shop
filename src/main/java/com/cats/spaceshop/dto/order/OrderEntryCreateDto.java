package com.cats.spaceshop.dto.order;

import com.cats.spaceshop.common.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({OrderEntryCreateDto.class, ExtendedValidation.class})
public class OrderEntryCreateDto {

    @NotNull(message = "Product ID is mandatory")
    UUID productId;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity;
}