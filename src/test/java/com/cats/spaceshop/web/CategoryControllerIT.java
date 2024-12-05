package com.cats.spaceshop.web;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static com.cats.spaceshop.constants.CategoryTestConstants.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Category Controller Integration Test with Real Database")
public class CategoryControllerIT extends AbstractIt {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired
    @SpyBean
    private CategoryRepository categoryRepository;

    @Autowired
    @SpyBean
    private CategoryService categoryService;

    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        reset(categoryService);

        categoryDto = CATEGORY_DTO;
        categoryCreateDto = CATEGORY_CREATE_DTO;
        categoryEntity = CATEGORY_ENTITY;

        categoryEntity = categoryRepository.save(categoryEntity);
    }

    @AfterEach
    void cleanUp(){
        categoryRepository.deleteAll();
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        List<CategoryDto> categories = categoryService.findAll();

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categories.size()))
                .andExpect(jsonPath("$[0].categoryId").value(categoryEntity.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(categoryDto.getName()));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        UUID categoryId = categoryEntity.getId();
        CategoryDto categoryFromDb = categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(categoryFromDb.getCategoryId().toString()))
                .andExpect(jsonPath("$.name").value(categoryFromDb.getName()));
    }

    @Test
    void shouldReturn404WhenCategoryNotFoundById() throws Exception {
        UUID nonExistingCategoryId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/categories/{id}", nonExistingCategoryId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Category with id " + nonExistingCategoryId + " not found exception"));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").isNotEmpty())
                .andExpect(jsonPath("$.name").value(categoryCreateDto.getName()));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        UUID categoryId = categoryEntity.getId();
        CategoryDto updatedCategoryDto = CategoryDto.builder()
                .categoryId(categoryId)
                .name("Updated Space Food")
                .description(categoryDto.getDescription())
                .build();

        mockMvc.perform(put("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(categoryId.toString()))
                .andExpect(jsonPath("$.name").value("Updated Space Food"));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        UUID categoryId = categoryEntity.getId();

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));

        boolean exists = categoryRepository.existsById(categoryId);
        assert !exists;
    }
}
