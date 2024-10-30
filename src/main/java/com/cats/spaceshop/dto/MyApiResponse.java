package com.cats.spaceshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
}