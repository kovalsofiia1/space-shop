package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.ProductRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.repository.entity.ProductEntity;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Math.log;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDetailsDto> findAll() {
        List<ProductEntity> products = productRepository.findAll();
        return productMapper.entityToDtoList(products);
    }

    @Override
    public Optional<ProductDetailsDto> findById(UUID id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return Optional.of(productMapper.entityToDto(product));
    }

    @Override
    public ProductDetailsDto save(ProductCreateDto productCreateDto) {
        CategoryEntity category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(productCreateDto.getCategoryId()));

        ProductEntity product = ProductEntity.builder()
                .category(category)  // Встановлюємо знайдену категорію
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .price(productCreateDto.getPrice())
                .stockQuantity(productCreateDto.getStockQuantity())
                .sku(productCreateDto.getSku())
                .build();

        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.entityToDto(savedProduct);
    }

    @Override
    public ProductDetailsDto update(ProductDetailsDto productDetailsDto) {
        if (productDetailsDto == null || productDetailsDto.getProductId() == null) {
            throw new IllegalArgumentException("Invalid product data");
        }

        ProductEntity existingProduct = productRepository.findById(productDetailsDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(productDetailsDto.getProductId()));

        CategoryEntity category = null;
        if(productDetailsDto.getCategoryId() != null) {
            category = categoryRepository.findById(productDetailsDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDetailsDto.getCategoryId()));
        }
        ProductEntity newProduct = ProductEntity.builder()
            .id(productDetailsDto.getProductId())
            .name(productDetailsDto.getName() != null ? productDetailsDto.getName() : existingProduct.getName())
            .description(productDetailsDto.getDescription() != null ? productDetailsDto.getDescription() : existingProduct.getDescription())
            .price(productDetailsDto.getPrice() != null ? productDetailsDto.getPrice() : existingProduct.getPrice())
            .stockQuantity(productDetailsDto.getStockQuantity() != null ? productDetailsDto.getStockQuantity() : existingProduct.getStockQuantity())
            .category(category != null ? category : existingProduct.getCategory())
            .sku(productDetailsDto.getSku() != null ? productDetailsDto.getSku() : existingProduct.getSku())
            .build();

        ProductEntity updatedProduct = productRepository.save(newProduct);
        return productMapper.entityToDto(updatedProduct);
    }

    @Override
    public void deleteById(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Optional<List<ProductDetailsDto>> findByCategory(UUID categoryId) {
        List<ProductEntity> filteredProducts = productRepository.findByCategoryId(categoryId);
        return Optional.of(productMapper.entityToDtoList(filteredProducts));
    }
}
