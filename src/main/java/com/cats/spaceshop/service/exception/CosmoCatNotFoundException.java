package com.cats.spaceshop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CosmoCatNotFoundException extends RuntimeException {
    private static final String CAT_NOT_FOUND_EXCEPTION = "Cosmo cat with id %s not found exception";

    public CosmoCatNotFoundException(UUID catId) {
        super(format(CAT_NOT_FOUND_EXCEPTION, catId));
    }
}