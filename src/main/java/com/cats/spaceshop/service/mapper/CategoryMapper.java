package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // Map CategoryCreateDto to Category and generate ID
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())") // Generate ID during mapping
    Category toCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(target = "id", source = "categoryId")
    Category toCategory(CategoryDto categoryCreateDto);

    @Mapping(target = "categoryId", source = "id")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "categoryId", source = "id")
    List<CategoryDto> toCategoryDtoList(List<Category> category);

    // Map Category to CategoryDto
    CategoryDto toCategoryDto(CategoryCreateDto category);
}