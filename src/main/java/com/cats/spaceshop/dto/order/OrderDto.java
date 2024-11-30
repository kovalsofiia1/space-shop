package com.cats.spaceshop.dto.order;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class OrderDto {
    UUID id;
    UUID cosmoCatId;
    List<OrderEntryDto> entriesList;
}