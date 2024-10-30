package com.cats.spaceshop.domain.product;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

import com.cats.spaceshop.domain.category.Category;

@Value
@Builder(toBuilder = true)
public class Product {

    UUID productId;
    String name;
    String description;
    String categoryId;
    BigDecimal price;
    int stockQuantity;
    String sku;
}