package com.cats.spaceshop.domain.order;

import com.cats.spaceshop.common.ExtendedValidation;
import jakarta.validation.GroupSequence;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ Order.class, ExtendedValidation.class })
public class Order {
    UUID Id;
    String customerId;
    List<OrderEntry> entriesList;
}
