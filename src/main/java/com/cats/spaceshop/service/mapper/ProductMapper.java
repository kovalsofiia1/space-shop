package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Map ProductCreateDto to Product entity and generate productId
    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID())") // Generate ID during mapping
    Product toEntity(ProductCreateDto productCreateDto);

    Product toEntity(ProductDetailsDto productCreateDto);

    ProductDetailsDto toDto(Product product);

    List<ProductDetailsDto> toDtoList(List<Product> products);
}
