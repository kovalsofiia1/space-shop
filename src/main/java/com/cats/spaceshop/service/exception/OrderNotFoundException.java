package com.cats.spaceshop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    private static final String ORDER_NOT_FOUND_EXCEPTION = "Order with id %s not found exception";

    public OrderNotFoundException(UUID orderId) {
        super(format(ORDER_NOT_FOUND_EXCEPTION, orderId));
    }
}