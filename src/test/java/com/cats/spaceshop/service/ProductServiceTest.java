package com.cats.spaceshop.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.impl.ProductServiceImpl;
import com.cats.spaceshop.service.mapper.ProductMapper;
import com.cats.spaceshop.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductCreateDto productCreateDto;
    private Product product;
    private ProductDetailsDto productDetailsDto;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        productCreateDto = ProductCreateDto.builder()
//                .name("Galactic Catnip Whiskers")
//                .description("A cosmic product.")
//                .categoryId("1")
//                .price(new BigDecimal(12.99))
//                .stockQuantity(100)
//                .sku("GCW-001")
//                .build();
//
//        product = Product.builder()
//                .productId(UUID.randomUUID())
//                .name("Galactic Catnip Whiskers")
//                .description("A cosmic product.")
//                .categoryId("1")
//                .price(new BigDecimal(12.99))
//                .stockQuantity(100)
//                .sku("GCW-001")
//                .build();
//
//        productDetailsDto = ProductDetailsDto.builder()
//                .productId(product.getProductId())
//                .name("Galactic Catnip Whiskers")
//                .description("A cosmic product.")
//                .categoryId("1")
//                .price(new BigDecimal(12.99))
//                .stockQuantity(100)
//                .sku("GCW-001")
//                .build();
//    }
//
//    @Test
//    public void testFindAllProducts() {
//        List<Product> productList = List.of(product);
//        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));
//
//        List<ProductDetailsDto> result = productService.findAll();
//
//        assertEquals(1, result.size());
//        assertEquals(productDetailsDto, result.get(0));
//        verify(productMapper, times(1)).toDtoList(anyList());
//    }
//
//    @Test
//    public void testFindProductById() {
//        UUID productId = product.getProductId();
//        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));
//
//        Optional<ProductDetailsDto> result = productService.findById(productId);
//
//        assertTrue(result.isPresent());
//        assertEquals(productDetailsDto, result.get());
//        verify(productMapper, times(1)).toDtoList(anyList());
//    }
//
//    @Test
//    public void testFindProductById_NotFound() {
//        UUID nonExistentProductId = UUID.randomUUID();
//        when(productMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());
//
//        Optional<ProductDetailsDto> result = productService.findById(nonExistentProductId);
//
//        // Verify that the ResourceNotFoundException is thrown
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> productService.findById(nonExistentProductId)
//        );
//        assertEquals("Product not found with ID: " + nonExistentProductId, exception.getMessage());
//        verify(productMapper, times(1)).toDtoList(anyList());
//    }
//
//    @Test
//    public void testSaveProduct() {
//        when(productMapper.toEntity(any(ProductCreateDto.class))).thenReturn(product);
//
//        MyApiResponse<String> response = productService.save(productCreateDto);
//
//        assertTrue(response.isSuccess());
//        assertEquals("Product created successfully!", response.getMessage());
//        verify(productMapper, times(1)).toEntity(productCreateDto);
//    }
//
//    @Test
//    public void testUpdateProduct() {
//        when(productMapper.toEntity(any(ProductDetailsDto.class))).thenReturn(product);
//
//        MyApiResponse<String> response = productService.update(productDetailsDto);
//
//        assertTrue(response.isSuccess());
//        assertEquals("Product updated successfully", response.getMessage());
//        verify(productMapper, times(1)).toEntity(productDetailsDto);
//    }
//
//    @Test
//    public void testDeleteProductById() {
//        // Arrange: Initialize products in the service
//        productService.save(productCreateDto); // Ensure the product is added first
//        UUID productId = product.getProductId();
//
//        // Act: Delete the product by ID
//        MyApiResponse<String> response = productService.deleteById(productId);
//
//        // Assert: Validate the response
//        assertTrue(response.isSuccess());
//        assertEquals("Product deleted successfully", response.getMessage());
//
//        // Verify that the product list no longer contains the deleted product
//        Optional<ProductDetailsDto> deletedProduct = productService.findById(productId);
//        assertTrue(deletedProduct.isEmpty());
//    }
//
//    @Test
//    public void testFindProductByIdNotFound() {
//        UUID nonExistentId = UUID.randomUUID(); // Generate a random UUID that doesn't exist
//
//        // Act & Assert: Verify that the exception is thrown
//        assertThrows(ResourceNotFoundException.class, () -> {
//            productService.findById(nonExistentId);
//        });
//    }
//
//    @Test
//    public void testFindByCategory() {
//        String categoryId = "1";
//        when(productMapper.toDtoList(anyList())).thenReturn(List.of(productDetailsDto));
//
//        Optional<List<ProductDetailsDto>> result = productService.findByCategory(categoryId);
//
//        assertTrue(result.isPresent());
//        assertEquals(1, result.get().size());
//        assertEquals(productDetailsDto, result.get().get(0));
//        verify(productMapper, times(1)).toDtoList(anyList());
//    }
}
