package com.cats.spaceshop.dto.order;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class OrderEntryDto {

    UUID productId;
    String productName;
    BigDecimal productPrice;
    Integer quantity;
}