package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.cats.spaceshop.constants.CategoryTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        categoryMapper = Mappers.getMapper(CategoryMapper.class);

        category = CATEGORY;

        categoryDto = CATEGORY_DTO;

        categoryCreateDto = CATEGORY_CREATE_DTO;

        categoryEntity = CATEGORY_ENTITY;
    }

    @Test
    void testToCreateCategory() {
        Category createdCategory = categoryMapper.toCreateCategory(categoryCreateDto);

        assertNotNull(createdCategory);
        assertEquals(categoryCreateDto.getName(), createdCategory.getName());
        assertEquals(categoryCreateDto.getDescription(), createdCategory.getDescription());
        assertNotNull(createdCategory.getId());
    }

    @Test
    void testToCategory() {
        Category mappedCategory = categoryMapper.toCategory(categoryDto);

        assertNotNull(mappedCategory);
        assertEquals(categoryDto.getCategoryId(), mappedCategory.getId());
        assertEquals(categoryDto.getName(), mappedCategory.getName());
        assertEquals(categoryDto.getDescription(), mappedCategory.getDescription());
    }

    @Test
    void testEntityToCategoryDto() {
        CategoryDto mappedCategoryDto = categoryMapper.entityToCategoryDto(categoryEntity);

        assertNotNull(mappedCategoryDto);
        assertEquals(categoryEntity.getId(), mappedCategoryDto.getCategoryId());
        assertEquals(categoryEntity.getName(), mappedCategoryDto.getName());
        assertEquals(categoryEntity.getDescription(), mappedCategoryDto.getDescription());
    }

    @Test
    void testToCategoryDto() {
        CategoryDto mappedCategoryDto = categoryMapper.toCategoryDto(category);

        assertNotNull(mappedCategoryDto);
        assertEquals(category.getId(), mappedCategoryDto.getCategoryId());
        assertEquals(category.getName(), mappedCategoryDto.getName());
        assertEquals(category.getDescription(), mappedCategoryDto.getDescription());
    }

    @Test
    void testEntityToCategoryDtoList() {
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        List<CategoryDto> categoryDtos = categoryMapper.entityToCategoryDtoList(categoryEntities);

        assertNotNull(categoryDtos);
        assertEquals(categoryEntities.size(), categoryDtos.size());
        assertEquals(categoryEntities.get(0).getId(), categoryDtos.get(0).getCategoryId());
        assertEquals(categoryEntities.get(0).getName(), categoryDtos.get(0).getName());
        assertEquals(categoryEntities.get(0).getDescription(), categoryDtos.get(0).getDescription());
    }

    @Test
    void testToCategoryDtoList() {
        List<Category> categories = List.of(category);
        List<CategoryDto> categoryDtos = categoryMapper.toCategoryDtoList(categories);

        assertNotNull(categoryDtos);
        assertEquals(categories.size(), categoryDtos.size());
        assertEquals(categories.get(0).getId(), categoryDtos.get(0).getCategoryId());
        assertEquals(categories.get(0).getName(), categoryDtos.get(0).getName());
        assertEquals(categories.get(0).getDescription(), categoryDtos.get(0).getDescription());
    }

    @Test
    void testToCategoryDtoFromCategoryCreateDto() {
        CategoryDto mappedCategoryDto = categoryMapper.toCategoryDto(categoryCreateDto);

        assertNotNull(mappedCategoryDto);
        assertEquals(categoryCreateDto.getName(), mappedCategoryDto.getName());
        assertEquals(categoryCreateDto.getDescription(), mappedCategoryDto.getDescription());
        assertNull(mappedCategoryDto.getCategoryId());
    }
}
