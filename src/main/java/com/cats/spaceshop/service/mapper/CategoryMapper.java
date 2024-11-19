package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    Category toCreateCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(target = "id", source = "categoryId")
    Category toCategory(CategoryDto categoryCreateDto);

    @Mapping(target = "categoryId", source = "id")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "categoryId", source = "id")
    List<CategoryDto> toCategoryDtoList(List<Category> category);

    CategoryDto toCategoryDto(CategoryCreateDto category);
}