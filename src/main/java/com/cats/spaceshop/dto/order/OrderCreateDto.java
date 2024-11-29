package com.cats.spaceshop.dto.order;

import com.cats.spaceshop.common.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({OrderCreateDto.class, ExtendedValidation.class})
public class OrderCreateDto {

    @NotNull(message = "Customer ID is mandatory")
    UUID cosmoCatId;

    @NotEmpty(message = "Order must contain at least one entry")
    @Valid
    List<OrderEntryCreateDto> entriesList;
}