package com.cats.spaceshop.service.exception;

import static java.lang.String.format;

public class CategoryNotFoundException extends RuntimeException {
    private static final String CATEGORY_NOT_FOUND_EXCEPTION = "Category with id %s not found exception";

    public CategoryNotFoundException(String categoryId) {
        super(format(CATEGORY_NOT_FOUND_EXCEPTION, categoryId));
    }
}