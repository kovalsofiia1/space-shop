package com.cats.spaceshop.constants;

import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.repository.entity.CategoryEntity;

import java.util.UUID;

public class CategoryTestConstants {

    public static final UUID CATEGORY_ID = UUID.randomUUID();
    public static final String CATEGORY_NAME = "Star Beds";
    public static final String CATEGORY_DESCRIPTION = "Beds for cats who love stargazing.";

    public static final Category CATEGORY = Category.builder()
            .id(CATEGORY_ID)
            .name(CATEGORY_NAME)
            .description(CATEGORY_DESCRIPTION)
            .build();

    public static final CategoryDto CATEGORY_DTO = CategoryDto.builder()
            .categoryId(CATEGORY_ID)
            .name(CATEGORY_NAME)
            .description(CATEGORY_DESCRIPTION)
            .build();


    public static final CategoryEntity CATEGORY_ENTITY = CategoryEntity.builder()
            .id(CATEGORY_ID)
            .name(CATEGORY_NAME)
            .description(CATEGORY_DESCRIPTION)
            .build();

    public static final CategoryCreateDto CATEGORY_CREATE_DTO = CategoryCreateDto.builder()
            .name(CATEGORY_NAME)
            .description(CATEGORY_DESCRIPTION)
            .build();
}
