package com.cats.spaceshop.domain.category;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Category {
    String id;
    String name;
    String description;
}