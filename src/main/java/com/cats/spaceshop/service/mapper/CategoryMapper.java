package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // Map CategoryCreateDto to Category and generate ID
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())") // Generate ID during mapping
    Category toCategory(CategoryCreateDto categoryCreateDto);

    // Map Category to CategoryDto
    CategoryDto toCategoryDto(CategoryCreateDto category);
}