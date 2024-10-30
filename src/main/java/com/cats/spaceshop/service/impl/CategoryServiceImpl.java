package com.cats.spaceshop.service.impl;

import org.springframework.stereotype.Service;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<CategoryDto> categories = new ArrayList<>();

    public CategoryServiceImpl() {
        categories.add(CategoryDto.builder()
                .categoryId("1")
                .name("Space Toys")
                .description("Toys for cats who love space adventures.")
                .build());

        categories.add(CategoryDto.builder()
                .categoryId("2")
                .name("Galactic Treats")
                .description("Delicious treats for your space-loving cats.")
                .build());

        categories.add(CategoryDto.builder()
                .categoryId("3")
                .name("Futuristic Scratching Posts")
                .description("Scratching posts designed for cats in the future.")
                .build());

        categories.add(CategoryDto.builder()
                .categoryId("4")
                .name("Intergalactic Catnip")
                .description("Catnip grown in zero gravity for maximum fun.")
                .build());
    }

    @Override
    public List<CategoryDto> findAll() {
        return new ArrayList<>(categories);
    }

    @Override
    public Optional<CategoryDto> findById(String categoryId) {
        return categories.stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .findFirst();
    }

    @Override
    public void save(CategoryDto categoryDto) {
        categories.add(categoryDto);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        deleteById(categoryDto.getCategoryId());
        categories.add(categoryDto);
    }

    @Override
    public void deleteById(String categoryId) {
        categories.removeIf(category -> category.getCategoryId().equals(categoryId));
    }
}