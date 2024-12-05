package com.cats.spaceshop.service.exception;

import java.util.UUID;

import static java.lang.String.format;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class CosmoCatNotFoundByEmailException extends RuntimeException{
    private static final String CAT_NOT_FOUND_EXCEPTION = "Cosmo cat with email %s not found exception";

    public CosmoCatNotFoundByEmailException(String email) {
        super(format(CAT_NOT_FOUND_EXCEPTION, email));
    }
}
