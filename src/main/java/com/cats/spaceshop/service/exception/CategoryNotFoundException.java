package com.cats.spaceshop.service.exception;

import java.util.UUID;

import static java.lang.String.format;

public class CategoryNotFoundException extends RuntimeException {
    private static final String CATEGORY_NOT_FOUND_EXCEPTION = "Category with id %s not found exception";

    public CategoryNotFoundException(UUID categoryId) {
        super(format(CATEGORY_NOT_FOUND_EXCEPTION, categoryId));
    }
}