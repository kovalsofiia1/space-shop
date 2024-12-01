package com.cats.spaceshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.ProductRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.repository.entity.ProductEntity;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
@DisplayName("Product Service Tests with Testcontainers")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceImplIT extends AbstractIt {

    @Autowired
    private ProductServiceImpl productService;

    @SpyBean
    @Autowired
    private ProductRepository productRepository;

    @SpyBean
    @Autowired
    private CategoryRepository categoryRepository;

    private static UUID newProductId;
    private static UUID newCategoryId;
    private static String newProductName = "Star Test Product new";
    private static String updatedProductName = "Updated Test Product";
    private static ProductEntity product;

    @BeforeEach
    void setUp() {
        reset(productRepository, categoryRepository);

        // Initial setup of category and product
        CategoryEntity category = CategoryEntity.builder()
                .name("Star Test Category")
                .description("Test Category Description")
                .build();
        categoryRepository.save(category);
        newCategoryId = category.getId();

        product = ProductEntity.builder()
                .category(category)
                .name("Star Test Product")
                .description("Test Product Description")
                .price(BigDecimal.valueOf(19.99))
                .stockQuantity(10)
                .sku("TEST-001")
                .build();

        product = productRepository.save(product);
        newProductId = product.getId();
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldReturnAllProducts() {
        var products = productService.findAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    @Order(2)
    void shouldReturnProductById() {
        ProductDetailsDto productDto = productService.findById(newProductId).orElse(null);
        assertNotNull(productDto);
        assertEquals(product.getName(), productDto.getName());
    }

    @Test
    @Order(3)
    void shouldThrowProductNotFoundExceptionWhenProductNotFoundById() {
        UUID randomId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class, () -> productService.findById(randomId).orElseThrow(() -> new ProductNotFoundException(randomId)));
    }

    @Test
    @Order(4)
    void shouldCreateProduct() {
        ProductCreateDto newProductCreateDto = ProductCreateDto.builder()
                .name(newProductName)
                .categoryId(newCategoryId)
                .description("New product description")
                .price(BigDecimal.valueOf(29.99))
                .stockQuantity(50)
                .sku("TEST-002")
                .build();

        ProductDetailsDto createdProduct = productService.save(newProductCreateDto);
        assertNotNull(createdProduct);
        assertEquals(newProductName, createdProduct.getName());
    }

    @Test
    @Order(5)
    void shouldUpdateProduct() {
        ProductDetailsDto existingProduct = productService.findById(newProductId).orElse(null);
        assertNotNull(existingProduct);

        ProductDetailsDto updatedProduct = ProductDetailsDto.builder()
                .productId(existingProduct.getProductId())
                .name(updatedProductName)
                .build();

        ProductDetailsDto result = productService.update(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProductName, result.getName());
    }

    @Test
    @Order(6)
    void shouldThrowProductNotFoundExceptionWhenUpdatingNonExistentProduct() {
        ProductDetailsDto nonExistentProduct = ProductDetailsDto.builder()
                .productId(UUID.randomUUID())
                .name("Nonexistent Product")
                .build();

        assertThrows(ProductNotFoundException.class, () -> productService.update(nonExistentProduct));
    }

    @Test
    @Order(7)
    void shouldDeleteProductById() {
        productService.deleteById(newProductId);
        assertThrows(ProductNotFoundException.class, () -> productService.findById(newProductId).orElseThrow(() -> new ProductNotFoundException(newProductId)));
    }

    @Test
    @Order(8)
    void shouldThrowProductNotFoundExceptionWhenDeletingNonExistentProduct() {
        UUID randomId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(randomId));
    }

    @Test
    @Order(9)
    void shouldReturnProductsByCategory() {
        var products = productService.findByCategory(newCategoryId);
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    @Order(10)
    void shouldThrowCategoryNotFoundExceptionWhenCategoryNotFound() {
        UUID randomCategoryId = UUID.randomUUID();
        assertEquals(Collections.emptyList(), productService.findByCategory(randomCategoryId));
    }
}
