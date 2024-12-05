package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.dto.product.ProductFullDetailsDto;
import com.cats.spaceshop.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.cats.spaceshop.constants.ProductTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    private ProductCreateDto productCreateDto;
    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);

        productCreateDto = PRODUCT_CREATE_DTO;

        product = PRODUCT;

        productEntity = PRODUCT_ENTITY;
    }

    @Test
    void testToDomainFromCreateDto() {
        Product mappedProduct = productMapper.toDomain(productCreateDto);

        assertNotNull(mappedProduct);
        assertNotNull(mappedProduct.getProductId()); // Ensure a UUID is generated
        assertEquals(productCreateDto.getName(), mappedProduct.getName());
        assertEquals(productCreateDto.getDescription(), mappedProduct.getDescription());
        assertEquals(productCreateDto.getPrice(), mappedProduct.getPrice());
        assertEquals(productCreateDto.getSku(), mappedProduct.getSku());
    }

    @Test
    void testEntityToDto() {
        ProductDetailsDto mappedDto = productMapper.entityToDto(productEntity);

        assertNotNull(mappedDto);
        assertEquals(productEntity.getId(), mappedDto.getProductId());
        assertEquals(productEntity.getName(), mappedDto.getName());
        assertEquals(productEntity.getDescription(), mappedDto.getDescription());
        assertEquals(productEntity.getPrice(), mappedDto.getPrice());
    }

    @Test
    void testToDto() {
        ProductDetailsDto mappedDto = productMapper.toDto(product);

        assertNotNull(mappedDto);
        assertEquals(product.getProductId(), mappedDto.getProductId());
        assertEquals(product.getName(), mappedDto.getName());
        assertEquals(product.getDescription(), mappedDto.getDescription());
        assertEquals(product.getPrice(), mappedDto.getPrice());
    }

    @Test
    void testEntityToDtoList() {
        List<ProductEntity> entities = List.of(productEntity);
        List<ProductDetailsDto> dtoList = productMapper.entityToDtoList(entities);

        assertNotNull(dtoList);
        assertEquals(entities.size(), dtoList.size());
        assertEquals(entities.get(0).getId(), dtoList.get(0).getProductId());
        assertEquals(entities.get(0).getName(), dtoList.get(0).getName());
    }
}
