package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.dto.product.ProductFullDetailsDto;
import com.cats.spaceshop.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID())") // Generate ID during mapping
    Product toDomain(ProductCreateDto productCreateDto);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "categoryId", source = "category.id")
    ProductDetailsDto entityToDto(ProductEntity product);

    ProductDetailsDto toDto(Product product);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "categoryId", source = "category.id")
    List<ProductDetailsDto> entityToDtoList(List<ProductEntity> products);
}
