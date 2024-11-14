package com.cats.spaceshop.web;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.featureToggle.FeatureToggleExtension;
import com.cats.spaceshop.featureToggle.FeatureToggles;
import com.cats.spaceshop.featureToggle.annotation.DisabledFeatureToggle;
import com.cats.spaceshop.featureToggle.annotation.EnabledFeatureToggle;

import com.cats.spaceshop.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.ProductTestConstants.PRODUCT_DETAILS_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DisplayName("Products Controller IT")
@ExtendWith(FeatureToggleExtension.class)
class ProductControllerIT extends AbstractIt {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet200() throws Exception {
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

    // Test for GET /products/category/{categoryId}
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
}


