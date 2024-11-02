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
    public  MyApiResponse<String> save(CategoryCreateDto categoryCreateDto) {
        categories.add(categoryMapper.toCreateCategory(categoryCreateDto));
        return new MyApiResponse<>(true, "Category created successfully!", null);
    }

    @Override
    public MyApiResponse<String> update(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getCategoryId() == null) {
            return new MyApiResponse<>(false, "Invalid category data", null);
        }

        // Check if the category exists before attempting to update
        boolean categoryExists = categories.stream()
                .anyMatch(category -> category.getId().equals(categoryDto.getCategoryId()));

        if (!categoryExists) {
            throw new CategoryNotFoundException("Category not found for update: " + categoryDto.getCategoryId());
        }

        // Proceed with the update
        deleteById(categoryDto.getCategoryId());
        categories.add(categoryMapper.toCategory(categoryDto));

        return new MyApiResponse<>(true, "Category updated successfully!", null);
    }

    @Override
    public void deleteById(String categoryId) {
        categories.removeIf(category -> category.getId().equals(categoryId));
    }
}