package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.CategoryTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryEntity categoryEntity;
    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryEntity = CATEGORY_ENTITY;
        categoryId = categoryEntity.getId();
        categoryDto = CATEGORY_DTO;
        categoryCreateDto = CATEGORY_CREATE_DTO;
    }

    @Test
    void testFindAll() {
        List<CategoryEntity> entities = List.of(categoryEntity);
        List<CategoryDto> dtos = List.of(categoryDto);

        when(categoryRepository.findAll()).thenReturn(entities);
        when(categoryMapper.entityToCategoryDtoList(entities)).thenReturn(dtos);

        List<CategoryDto> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryDto, result.get(0));
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).entityToCategoryDtoList(entities);
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.entityToCategoryDto(categoryEntity)).thenReturn(categoryDto);

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryDto, result.get());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).entityToCategoryDto(categoryEntity);
    }

    @Test
    void testFindById_NotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testSave() {
        when(categoryMapper.entityToCreateCategory(categoryCreateDto)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.entityToCategoryDto(categoryEntity)).thenReturn(categoryDto);

        CategoryDto result = categoryService.save(categoryCreateDto);

        assertNotNull(result);
        assertEquals(categoryDto, result);
        verify(categoryMapper, times(1)).entityToCreateCategory(categoryCreateDto);
        verify(categoryRepository, times(1)).save(categoryEntity);
        verify(categoryMapper, times(1)).entityToCategoryDto(categoryEntity);
    }

    @Test
    void testUpdate() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        when(categoryMapper.entityToCategoryDto(categoryEntity)).thenReturn(categoryDto);

        CategoryDto result = categoryService.update(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
        verify(categoryMapper, times(1)).entityToCategoryDto(categoryEntity);
    }

    @Test
    void testUpdate_NotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.update(categoryDto);
        });

        assertEquals("Category with id " + categoryId + " not found exception", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteById(categoryId);

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteById_NotFound() {
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteById(categoryId);
        });

        assertEquals("Category with id " + categoryId + " not found exception", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }
}
