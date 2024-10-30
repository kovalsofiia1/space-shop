package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Map ProductCreateDto to ProductDetailsDto and generate productId
    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID())") // Generate ID during mapping
    ProductDetailsDto toEntity(ProductCreateDto productCreateDto);
}
