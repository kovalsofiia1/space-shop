package com.cats.spaceshop.service;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.repository.projection.ProductReportProjection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public interface ProductService {
    List<ProductDetailsDto> findAll();
    Optional<ProductDetailsDto> findById(UUID productId);
    ProductDetailsDto save(ProductCreateDto product);
    ProductDetailsDto update(ProductDetailsDto product);
    void deleteById(UUID productId);
    List<ProductDetailsDto> findByCategory(UUID categoryId);
    List<ProductReportProjection> getMostPopularProducts();
}