package com.cats.spaceshop.service;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAll();

    Optional<CategoryDto> findById(String categoryId);

    void save(CategoryCreateDto categoryDto);

    void update(CategoryDto categoryDto);

    void deleteById(String categoryId);
}