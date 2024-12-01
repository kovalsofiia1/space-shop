
package com.cats.spaceshop.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@DisplayName("Category Service Tests with Testcontainers")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceImplIT extends AbstractIt {

    @Autowired private CategoryService categoryService;

    @SpyBean
    @Autowired
    private CategoryRepository categoryRepository;

    private static UUID newCategoryId;
    private static String newCategoryName = "Test Category";
    private static String updatedCategoryName = "Updated Test Category";

    @AfterEach
    void cleanUp() {
        categoryRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        reset(categoryRepository);

        // Initial setup of category
        CategoryEntity category1 = CategoryEntity.builder()
                .name("Star Category 1")
                .description("Star Test Category 1")
                .build();
        categoryRepository.save(category1);

        newCategoryId = category1.getId();
    }

    @Test
    @Order(1)
    void shouldReturnAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    @Order(2)
    void shouldReturnCategoryById() {
        CategoryDto categoryDto = categoryService.findById(newCategoryId).orElse(null);
        assertNotNull(categoryDto);
        assertEquals("Star Category 1", categoryDto.getName());
    }

    @Test
    @Order(3)
    void shouldThrowCategoryNotFoundExceptionWhenCategoryNotFoundById() {
        UUID randomId = UUID.randomUUID();
        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(randomId).orElseThrow(() -> new CategoryNotFoundException(randomId)));
    }

    @Test
    @Order(4)
    void shouldCreateCategory() {
        CategoryCreateDto newCategoryCreateDto = CategoryCreateDto.builder()
                .name(newCategoryName)
                .description("Test category description")
                .build();

        CategoryDto createdCategory = categoryService.save(newCategoryCreateDto);
        assertNotNull(createdCategory);
        assertEquals(newCategoryName, createdCategory.getName());
    }

    @Test
    @Order(5)
    void shouldUpdateCategory() {
        CategoryDto existingCategory = categoryService.findById(newCategoryId).orElse(null);
        assertNotNull(existingCategory);

        CategoryDto newCategory = CategoryDto.builder()
                .categoryId(existingCategory.getCategoryId())
                .name(updatedCategoryName)
                .build();

//        existingCategory.setName(updatedCategoryName);
        CategoryDto updatedCategory = categoryService.update(newCategory);
        assertNotNull(updatedCategory);
        assertEquals(updatedCategoryName, updatedCategory.getName());
    }

    @Test
    @Order(6)
    void shouldThrowCategoryNotFoundExceptionWhenUpdatingNonExistentCategory() {
        CategoryDto nonExistentCategory = CategoryDto.builder()
                .categoryId(UUID.randomUUID())
                .name("Nonexistent Category")
                .build();

        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(nonExistentCategory));
    }

    @Test
    @Order(7)
    void shouldDeleteCategoryById() {
        categoryService.deleteById(newCategoryId);
        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(newCategoryId).orElseThrow(() -> new CategoryNotFoundException(newCategoryId)));
    }

    @Test
    @Order(8)
    void shouldThrowCategoryNotFoundExceptionWhenDeletingNonExistentCategory() {
        UUID randomId = UUID.randomUUID();
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(randomId));
    }
}
