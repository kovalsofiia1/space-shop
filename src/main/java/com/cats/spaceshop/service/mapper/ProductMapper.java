package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {
    public ProductDetailsDto toEntity(ProductCreateDto productCreateDto) {
        ProductDetailsDto productDetails = ProductDetailsDto.builder()
                .productId(UUID.randomUUID()) // Generate a new UUID for the productId
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .categoryId(productCreateDto.getCategoryId())
                .price(productCreateDto.getPrice())
                .stockQuantity(productCreateDto.getStockQuantity())
                .sku(productCreateDto.getSku()) // This can be set or left null if optional
                .build();

        return productDetails;
    }
}
