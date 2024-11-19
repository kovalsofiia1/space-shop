package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.ProductTestConstants.PRODUCT_CREATE_DTO;
import static com.cats.spaceshop.constants.ProductTestConstants.PRODUCT_DETAILS_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private ProductDetailsDto productDetailsDto;
    private ProductCreateDto productCreateDto;


    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        productCreateDto = PRODUCT_CREATE_DTO;
        productDetailsDto = PRODUCT_DETAILS_DTO;
    }

    @Test
    void getAllProducts_ShouldReturnProductList() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(List.of(productDetailsDto));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    void getProductById_ShouldReturnProductDetails() throws Exception {
        Mockito.when(productService.findById(eq(productDetailsDto.getProductId()))).thenReturn(Optional.of(productDetailsDto));

        mockMvc.perform(get("/api/v1/products/{id}", productDetailsDto.getProductId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDetailsDto.getName()));
    }

    @Test
    void getProductById_ShouldReturnNotFound() throws Exception {
        Mockito.when(productService.findById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductByCategory_ShouldReturnProductList() throws Exception {
        Mockito.when(productService.findByCategory(anyString())).thenReturn(Optional.of(List.of(productDetailsDto)));

        mockMvc.perform(get("/api/v1/products/category/{categoryId}", "category-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        Mockito.when(productService.save(any(ProductCreateDto.class))).thenReturn(productDetailsDto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDetailsDto.getName()));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {

         ProductDetailsDto updated = ProductDetailsDto.builder()
                .productId(productDetailsDto.getProductId())
                .name(productDetailsDto.getName() + "updated")
                .description(productDetailsDto.getDescription())
                .categoryId(productDetailsDto.getCategoryId())
                .price(productDetailsDto.getPrice())
                .stockQuantity(productDetailsDto.getStockQuantity())
                .sku(productDetailsDto.getSku())
                .build();
        Mockito.when(productService.update(any(ProductDetailsDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/products/{id}", productDetailsDto.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updated.getName()));
    }

    @Test
    void updateProduct_ShouldReturnBadRequest_WhenIdMismatch() throws Exception {

        mockMvc.perform(put("/api/v1/products/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDetailsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product ID in the request body does not match the path variable."));
    }

    @Test
    void deleteProduct_ShouldReturnSuccessMessage() throws Exception {
        Mockito.doNothing().when(productService).deleteById(any(UUID.class));

        mockMvc.perform(delete("/api/v1/products/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }
}
