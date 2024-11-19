package com.cats.spaceshop.constants;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.domain.product.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductTestConstants {

    public static final UUID PRODUCT_ID = UUID.fromString("a5acbe53-4caf-43cd-ab5f-f26723f327e0");
    public static final String CATEGORY_ID = "1";
    public static final String PRODUCT_NAME = "Galactic Catnip Whiskers";
    public static final String PRODUCT_DESCRIPTION = "A cosmic product.";
    public static final BigDecimal PRODUCT_PRICE = new BigDecimal(12.99);
    public static final int PRODUCT_STOCK_QUANTITY = 100;
    public static final String PRODUCT_SKU = "GCW-001";

    public static final ProductCreateDto PRODUCT_CREATE_DTO = ProductCreateDto.builder()
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESCRIPTION)
            .categoryId(CATEGORY_ID)
            .price(PRODUCT_PRICE)
            .stockQuantity(PRODUCT_STOCK_QUANTITY)
            .sku(PRODUCT_SKU)
            .build();

    public static final Product PRODUCT = Product.builder()
            .productId(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESCRIPTION)
            .categoryId(CATEGORY_ID)
            .price(PRODUCT_PRICE)
            .stockQuantity(PRODUCT_STOCK_QUANTITY)
            .sku(PRODUCT_SKU)
            .build();

    public static final ProductDetailsDto PRODUCT_DETAILS_DTO = ProductDetailsDto.builder()
            .productId(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESCRIPTION)
            .categoryId(CATEGORY_ID)
            .price(PRODUCT_PRICE)
            .stockQuantity(PRODUCT_STOCK_QUANTITY)
            .sku(PRODUCT_SKU)
            .build();
}
