package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = Category.builder()
                .id("5")
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();

        categoryDto = CategoryDto.builder()
                .categoryId("5")
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();

        categoryCreateDto = CategoryCreateDto.builder()
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();

        categoryService.categories.add(category);
    }

    @Test
    void testFindAllCategories() {
        List<CategoryDto> categoryDtos = List.of(categoryDto);
        when(categoryMapper.toCategoryDtoList(anyList())).thenReturn(categoryDtos);

        List<CategoryDto> result = categoryService.findAll();

        assertEquals(categoryDtos.size(), result.size()); // Assuming initialized categories
        verify(categoryMapper, times(1)).toCategoryDtoList(anyList());
    }

    @Test
    void testFindCategoryById() {
        String categoryId = "5";
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        // Add the category to the list
        categoryService.save(categoryCreateDto);

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryDto, result.get());
        verify(categoryMapper, times(1)).toCategoryDto(any(Category.class));
    }

    @Test
    void testFindCategoryByIdNotFound() {
        String nonExistentId = "999";
        Optional<CategoryDto> result = categoryService.findById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveCategory() {
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);

        MyApiResponse<String> response = categoryService.save(categoryCreateDto);

        assertTrue(response.isSuccess());
        assertEquals("Category created successfully!", response.getData());

        verify(categoryMapper, times(1)).toCreateCategory(categoryCreateDto);
    }

    @Test
    void testUpdateCategory() {
        String categoryId = category.getId();
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);
        when(categoryMapper.toCategory(categoryDto)).thenReturn(category);

        categoryService.save(categoryCreateDto);

        MyApiResponse<String> response = categoryService.update(categoryDto);

        assertTrue(response.isSuccess());
        assertEquals("Category updated successfully!", response.getData());

        verify(categoryMapper, times(1)).toCategory(categoryDto);
    }

    @Test
    void testUpdateNotExistCategory() {

        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);

        categoryService.save(categoryCreateDto);

        String categoryId = category.getId(); // Assuming this gets the correct ID
        categoryService.deleteById(categoryId);

        // Attempt to update a non-existent category
        CategoryNotFoundException thrown = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.update(categoryDto),
                "Expected CategoryNotFoundException for non-existent category"
        );

        assertEquals("Category not found for update: " + categoryId, thrown.getMessage());
        verify(categoryMapper, times(1)).toCreateCategory(categoryCreateDto);
    }

    @Test
    void testDeleteCategoryById() {
        String categoryId = category.getId();

        // Save initially to ensure it exists
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);
        categoryService.save(categoryCreateDto);

        categoryService.deleteById(categoryId);

        Optional<CategoryDto> result = categoryService.findById(categoryId);
        assertFalse(result.isPresent());
    }
}
