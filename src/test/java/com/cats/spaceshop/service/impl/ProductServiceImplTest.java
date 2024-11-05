package com.cats.spaceshop.service.impl;

import static com.cats.spaceshop.constants.ProductTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;


import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.ProductMapper;
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

        productCreateDto = PRODUCT_CREATE_DTO;
        product = PRODUCT;
        productDetailsDto = PRODUCT_DETAILS_DTO;

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
        UUID nonExistentId = UUID.randomUUID();

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findById(nonExistentId);
        });
    }

    @Test
    public void testSaveProduct() {
        when(productMapper.toEntity(productCreateDto)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDetailsDto);

        ProductDetailsDto response = productService.save(productCreateDto);

        assertNotNull(response);
        assertEquals(productDetailsDto, response);
        verify(productMapper, times(1)).toEntity(productCreateDto);
    }

    @Test
    public void testUpdateProduct() {
        when(productMapper.toEntity(any(ProductDetailsDto.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDetailsDto);

        ProductDetailsDto response = productService.update(productDetailsDto);


        assertNotNull(response);
        assertEquals(productDetailsDto, response);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    public void testUpdateNonExistentProduct() {
        when(productMapper.toEntity(any(ProductCreateDto.class))).thenReturn(product);
        productService.save(productCreateDto);
        UUID productId = product.getProductId();
        productService.deleteById(productId);

        when(productMapper.toEntity(any(ProductDetailsDto.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDetailsDto);

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
        when(productService.save(productCreateDto)).thenReturn(productDetailsDto);
        ProductDetailsDto created = productService.save(productCreateDto);

        UUID productId = created.getProductId();

        productService.deleteById(productId);

        assertThrows(ProductNotFoundException.class, () -> productService.findById(productId));
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