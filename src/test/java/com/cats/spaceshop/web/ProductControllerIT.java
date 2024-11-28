package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.featureToggle.FeatureToggleExtension;
import com.cats.spaceshop.featureToggle.FeatureToggles;
import com.cats.spaceshop.featureToggle.annotation.DisabledFeatureToggle;
import com.cats.spaceshop.featureToggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.ProductTestConstants.PRODUCT_DETAILS_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.cats.spaceshop.constants.ProductTestConstants.PRODUCT_CREATE_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Products Controller IT")
@ExtendWith(FeatureToggleExtension.class)
class ProductControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;
    private ProductDetailsDto productDetailsDto;
    private ProductCreateDto productCreateDto;


    @BeforeEach
    void setUp() {
        productCreateDto = PRODUCT_CREATE_DTO;
        productDetailsDto = PRODUCT_DETAILS_DTO;
    }

    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGetProductsDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGetProductsEnabled() throws Exception {
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());
    }


    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet404ForGetProductById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet200ForGetProductById() throws Exception {
        ProductDetailsDto productDetailsDto = PRODUCT_DETAILS_DTO;
        UUID id = productDetailsDto.getProductId();
        when(productService.findById(id)).thenReturn(Optional.of(productDetailsDto));

        mockMvc.perform(get("/api/v1/products/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet404ForGetProductByCategoryFeatureDisabled() throws Exception {
        String categoryId = "some-category-id";
        mockMvc.perform(get("/api/v1/products/category/{categoryId}", categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet200ForGetProductByCategory() throws Exception {
        String categoryId = "some-category-id";
        List<ProductDetailsDto> products = List.of(PRODUCT_DETAILS_DTO);
        when(productService.findByCategory(categoryId)).thenReturn(Optional.of(products));

        mockMvc.perform(get("/api/v1/products/category/{categoryId}", categoryId))
                .andExpect(status().isOk());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void getAllProducts_ShouldReturnProductList() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(List.of(productDetailsDto));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
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
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void getProductByCategory_ShouldReturnProductList() throws Exception {
        Mockito.when(productService.findByCategory(anyString())).thenReturn(Optional.of(List.of(productDetailsDto)));

        mockMvc.perform(get("/api/v1/products/category/{categoryId}", "category-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
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
