package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categoryMapper.entityToCategoryDtoList(categories);
    }

    @Override
    public Optional<CategoryDto> findById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::entityToCategoryDto);
    }

    @Override
    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
        CategoryEntity category = categoryMapper.entityToCreateCategory(categoryCreateDto);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.entityToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }

        CategoryEntity existingCategory = categoryRepository.findById(categoryDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getCategoryId()));

        CategoryEntity newCategory = CategoryEntity.builder()
        .id(existingCategory.getId())
        .name(categoryDto.getName() != null ? categoryDto.getName() : existingCategory.getName())
        .description(categoryDto.getDescription() != null ? categoryDto.getDescription() : existingCategory.getDescription())
        .build();

        CategoryEntity updatedCategory = categoryRepository.save(newCategory);
        return categoryMapper.entityToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteById(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
