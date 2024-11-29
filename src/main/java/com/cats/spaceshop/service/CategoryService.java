package com.cats.spaceshop.service;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> findAll();

    Optional<CategoryDto> findById(UUID categoryId);

    CategoryDto save(CategoryCreateDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void deleteById(UUID categoryId);
}