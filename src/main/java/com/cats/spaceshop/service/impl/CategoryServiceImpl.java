package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    final List<Category> categories = new ArrayList<>();

    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
        initializeCategories();

    }

    private void initializeCategories(){
        categories.add(Category.builder()
                .id("1")
                .name("Space Toys")
                .description("Toys for cats who love space adventures.")
                .build());

        categories.add(Category.builder()
                .id("2")
                .name("Galactic Treats")
                .description("Delicious treats for your space-loving cats.")
                .build());

        categories.add(Category.builder()
                .id("3")
                .name("Futuristic Scratching Posts")
                .description("Scratching posts designed for cats in the future.")
                .build());

        categories.add(Category.builder()
                .id("4")
                .name("Intergalactic Catnip")
                .description("Catnip grown in zero gravity for maximum fun.")
                .build());
    }

    @Override
    public List<CategoryDto> findAll() {
        return new ArrayList<>(categoryMapper.toCategoryDtoList(categories));
    }

    @Override
    public Optional<CategoryDto> findById(String categoryId) {
        return categories.stream()
                .filter(category -> category.getId().equals(categoryId))
                .findFirst()
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.toCreateCategory(categoryCreateDto);
        categories.add(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }

        Category existingCategory = categories.stream()
                .filter(cat -> cat.getId().equals(categoryDto.getCategoryId()))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException("Category not found for update: " + categoryDto.getCategoryId()));

        Category updatedCategory = Category.builder()
                .id(existingCategory.getId())
                .name(categoryDto.getName() != null ? categoryDto.getName() : existingCategory.getName())
                .description(categoryDto.getDescription() != null ? categoryDto.getDescription() : existingCategory.getDescription())
                .build();

        categories.remove(existingCategory);
        categories.add(updatedCategory);

        return categoryMapper.toCategoryDto(updatedCategory);
    }

    @Override
    public void deleteById(String categoryId) {
        boolean removed = categories.removeIf(category -> category.getId().equals(categoryId));
        if (!removed) {
            throw new CategoryNotFoundException("Category not found for deletion: " + categoryId);
        }
    }
}