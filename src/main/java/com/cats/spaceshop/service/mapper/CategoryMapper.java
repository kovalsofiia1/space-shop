package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    CategoryEntity entityToCreateCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Category toCreateCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(target = "id", source = "categoryId")
    Category toCategory(CategoryDto categoryCreateDto);

    @Mapping(target = "categoryId", source = "id")
    CategoryDto entityToCategoryDto(CategoryEntity category);

    @Mapping(target = "categoryId", source = "id")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "categoryId", source = "id")
    List<CategoryDto> entityToCategoryDtoList(List<CategoryEntity> category);

    @Mapping(target = "categoryId", source = "id")
    List<CategoryDto> toCategoryDtoList(List<Category> category);

    CategoryDto toCategoryDto(CategoryCreateDto category);
}