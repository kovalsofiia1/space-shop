package com.cats.spaceshop.domain.order;

import com.cats.spaceshop.common.ExtendedValidation;
import com.cats.spaceshop.domain.product.Product;
import jakarta.validation.GroupSequence;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ OrderEntry.class, ExtendedValidation.class })
public class OrderEntry {

    Product product;
    int quantity;
}
