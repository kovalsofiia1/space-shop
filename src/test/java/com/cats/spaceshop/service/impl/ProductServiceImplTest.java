package com.cats.spaceshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.ProductMapper;
import com.cats.spaceshop.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductCreateDto productCreateDto;
    private Product product;
    private ProductDetailsDto productDetailsDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productCreateDto = ProductCreateDto.builder()
                .name("Galactic Catnip Whiskers")
                .description("A cosmic product.")
                .categoryId("1")
                .price(new BigDecimal(12.99))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();

        product = Product.builder()
                .productId(UUID.fromString("a5acbe53-4caf-43cd-ab5f-f26723f327e0"))
                .name("Galactic Catnip Whiskers")
                .description("A cosmic product.")
                .categoryId("1")
                .price(new BigDecimal(12.99))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();

        productDetailsDto = ProductDetailsDto.builder()
                .productId(product.getProductId())
                .name("Galactic Catnip Whiskers")
                .description("A cosmic product.")
                .categoryId("1")
                .price(new BigDecimal(12.99))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();

        // Add product to productService's in-memory list
        productService.products.add(product);
    }
    @Test
    public void testFindAllProducts() {
        List<Product> productList = List.of(product);
        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));

        List<ProductDetailsDto> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(productDetailsDto, result.get(0));
        verify(productMapper, times(1)).toDtoList(anyList());
    }

    @Test
    public void testFindProductById() {
        UUID productId = product.getProductId();
        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));

        Optional<ProductDetailsDto> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(productDetailsDto, result.get());
        verify(productMapper, times(1)).toDtoList(anyList());
    }

    @Test
    public void testFindProductByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID(); // Generate a random UUID that doesn't exist

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistentId);
        });
    }

    @Test
    public void testSaveProduct() {
        when(productMapper.toEntity(any(ProductCreateDto.class))).thenReturn(product);

        MyApiResponse<String> response = productService.save(productCreateDto);

        assertTrue(response.isSuccess());
        assertEquals("Product created successfully!", response.getData());
        verify(productMapper, times(1)).toEntity(productCreateDto);
    }

    @Test
    public void testUpdateProduct() {
        when(productMapper.toEntity(any(ProductDetailsDto.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDetailsDto);

        MyApiResponse<String> response = productService.update(productDetailsDto);

        assertTrue(response.isSuccess());
        assertEquals("Product updated successfully", response.getData());
        verify(productMapper, times(1)).toEntity(productDetailsDto);
    }

    @Test
    public void testUpdateNonExistentProduct() {
        when(productMapper.toEntity(any(ProductCreateDto.class))).thenReturn(product);
        productService.save(productCreateDto);  // Ensure product is saved first

        UUID productId = product.getProductId();
        productService.deleteById(productId); //delete saved product

        when(productMapper.toEntity(any(ProductDetailsDto.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDetailsDto);

        // attempt to update a non-existent product and expect an exception
        ProductNotFoundException thrown = assertThrows(
                ProductNotFoundException.class,
                () -> productService.update(productDetailsDto),
                "Expected ProductNotFoundException for non-existent product"
        );

        assertEquals("Product not found for update: " + productDetailsDto.getProductId(), thrown.getMessage());
    }

    @Test
    public void testDeleteProductById() {
        when(productMapper.toEntity(any(ProductCreateDto.class))).thenReturn(product);
        productService.save(productCreateDto);  // Ensure product is saved first

        UUID productId = product.getProductId();
        MyApiResponse<String> response = productService.deleteById(productId);

        assertTrue(response.isSuccess());
        assertEquals("Product deleted successfully", response.getData());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(productId));
    }

    @Test
    public void testFindByCategory() {
        String categoryId = "1";
        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));

        Optional<List<ProductDetailsDto>> result = productService.findByCategory(categoryId);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(productDetailsDto, result.get().get(0));
        verify(productMapper, times(1)).toDtoList(anyList());
    }
}