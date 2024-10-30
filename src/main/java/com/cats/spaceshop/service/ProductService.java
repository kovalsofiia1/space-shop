package com.cats.spaceshop.service;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.ApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public interface ProductService {
    List<ProductDetailsDto> findAll();

    Optional<ProductDetailsDto> findById(UUID productId);

    ApiResponse<String> save(ProductCreateDto product);
    ApiResponse<String> update(ProductDetailsDto product);
    ApiResponse<String> deleteById(UUID productId);
    Optional<List<ProductDetailsDto>> findByCategory(String categoryId);
}