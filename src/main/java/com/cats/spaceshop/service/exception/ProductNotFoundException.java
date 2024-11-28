package com.cats.spaceshop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "Product with id %s not found exception";

    public ProductNotFoundException(UUID productId) {
        super(format(PRODUCT_NOT_FOUND_EXCEPTION, productId));
    }
}