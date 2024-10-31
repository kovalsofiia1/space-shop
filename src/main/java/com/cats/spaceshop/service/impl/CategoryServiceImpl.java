package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = new ArrayList<>();

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
    public void save(CategoryCreateDto categoryCreateDto) {
        categories.add(categoryMapper.toCategory(categoryCreateDto));
    }

    @Override
    public void update(CategoryDto categoryDto) {
        deleteById(categoryDto.getCategoryId());
        categories.add(categoryMapper.toCategory(categoryDto));
    }

    @Override
    public void deleteById(String categoryId) {
        categories.removeIf(category -> category.getId().equals(categoryId));
    }
}