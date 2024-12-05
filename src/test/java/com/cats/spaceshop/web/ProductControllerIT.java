package com.cats.spaceshop.web;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.ProductRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.repository.entity.ProductEntity;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_ENTITY;
import static com.cats.spaceshop.constants.ProductTestConstants.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Testcontainers
@DisplayName("Product Controller Integration Test with Real Database")
public class ProductControllerIT extends AbstractIt {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired
    @SpyBean
    private ProductRepository productRepository;

    @Autowired
    @SpyBean
    private CategoryRepository categoryRepository;

    @Autowired
    @SpyBean
    private ProductService productService;

    private ProductDetailsDto productDetailsDto;
    private ProductCreateDto productCreateDto;
    private ProductEntity productEntity;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        reset(productService);

        categoryEntity = CATEGORY_ENTITY;
        categoryEntity = categoryRepository.save(categoryEntity);

        productEntity = ProductEntity.builder()
                .name("star dust")
                .description("star dust description")
                .category(categoryEntity)
                .price(BigDecimal.valueOf(10.00))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();

        productEntity = productRepository.save(productEntity);

        productDetailsDto = ProductDetailsDto.builder()
                .productId(productEntity.getId())
                .name("star dust")
                .description("star dust description")
                .categoryId(categoryEntity.getId())
                .price(BigDecimal.valueOf(10.00))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();

        productCreateDto = ProductCreateDto.builder()
                .name("star dust")
                .description("star dust description")
                .categoryId(categoryEntity.getId())
                .price(BigDecimal.valueOf(10.00))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        List<ProductDetailsDto> products = productService.findAll();

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size()))
                .andExpect(jsonPath("$[0].productId").value(productEntity.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    void shouldReturnAllProductsByCategory() throws Exception {
        mockMvc.perform(get("/api/v1/products/category/{categoryId}", categoryEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].productId").value(productEntity.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(productDetailsDto.getName()));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        UUID productId = productEntity.getId();
        ProductDetailsDto productFromDb = productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productFromDb.getProductId().toString()))
                .andExpect(jsonPath("$.name").value(productFromDb.getName()));
    }

    @Test
    void shouldReturn404WhenProductNotFoundById() throws Exception {
        UUID nonExistingProductId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/products/{id}", nonExistingProductId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Product with id " + nonExistingProductId + " not found exception"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        UUID productId = productEntity.getId();
        ProductDetailsDto updatedProductDto = ProductDetailsDto.builder()
                .productId(productId)
                .name("Updated Space Toy")
                .description(productDetailsDto.getDescription())
                .categoryId(categoryEntity.getId())
                .price(productDetailsDto.getPrice())
                .stockQuantity(productDetailsDto.getStockQuantity())
                .sku(productDetailsDto.getSku())
                .build();

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.name").value("Updated Space Toy"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        UUID productId = productEntity.getId();

        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));

        boolean exists = productRepository.existsById(productId);
        assert !exists;
    }
}
