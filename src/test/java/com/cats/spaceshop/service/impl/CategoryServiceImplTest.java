package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.cats.spaceshop.constants.CategoryTestConstants.*;
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

        category = CATEGORY;
        categoryDto = CATEGORY_DTO;
        categoryCreateDto = CATEGORY_CREATE_DTO;
    }

    @Test
    void testFindAllCategories() {
        List<CategoryDto> categoryDtos = List.of(categoryDto);
        when(categoryMapper.toCategoryDtoList(anyList())).thenReturn(categoryDtos);

        List<CategoryDto> result = categoryService.findAll();

        assertEquals(categoryDtos.size(), result.size());
        verify(categoryMapper, times(1)).toCategoryDtoList(anyList());
    }

    @Test
    void testFindCategoryById() {
        String categoryId = "5";
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);

        categoryService.save(categoryCreateDto);

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryDto, result.get());
        verify(categoryMapper, times(1)).toCreateCategory(categoryCreateDto);
        verify(categoryMapper, times(2)).toCategoryDto(category);
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
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto response = categoryService.save(categoryCreateDto);

        assertNotNull(response);
        assertEquals(categoryDto, response);
        verify(categoryMapper, times(1)).toCreateCategory(categoryCreateDto);
        verify(categoryMapper, times(1)).toCategoryDto(category);
    }

    @Test
    void testUpdateCategory() {
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        categoryService.save(categoryCreateDto);

        CategoryDto response = categoryService.update(categoryDto);

        assertNotNull(response);
        assertEquals(categoryDto, response);
        verify(categoryMapper, times(2)).toCategoryDto(category);
    }

    @Test
    void testUpdateNotExistCategory() {
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);

        categoryService.save(categoryCreateDto);
        String categoryId = category.getId();
        categoryService.deleteById(categoryId);

        CategoryNotFoundException thrown = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.update(categoryDto),
                "Expected CategoryNotFoundException for non-existent category"
        );

        assertEquals("Category not found for update: " + categoryDto.getCategoryId(), thrown.getMessage());
    }

    @Test
    void testDeleteCategoryById() {
        String categoryId = category.getId();
        when(categoryMapper.toCreateCategory(categoryCreateDto)).thenReturn(category);
        categoryService.save(categoryCreateDto);

        categoryService.deleteById(categoryId);

        Optional<CategoryDto> result = categoryService.findById(categoryId);
        assertFalse(result.isPresent());
    }
}
