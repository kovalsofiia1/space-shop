package com.cats.spaceshop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.CONFLICT)

public class CosmoCatWithEmailAlreadyExistsException extends RuntimeException{
    private static final String CAT_EXISTS_EXCEPTION = "Cosmo cat with email %s already exists exception";

    public CosmoCatWithEmailAlreadyExistsException(String email) {
        super(format(CAT_EXISTS_EXCEPTION, email));
    }
}
