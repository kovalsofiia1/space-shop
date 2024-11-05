package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    private ProductCreateDto productCreateDto;
    private Product product;
    private ProductDetailsDto productDetailsDto;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);

        productCreateDto = ProductCreateDto.builder()
                .name("Cat Toy")
                .description("A fun toy for cats.")
                .price(new BigDecimal(9.99))
                .build();

        product = Product.builder()
                .productId(UUID.randomUUID())
                .name("Cat Toy")
                .description("A fun toy for cats.")
                .price(new BigDecimal(9.99))
                .build();

        productDetailsDto = ProductDetailsDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    @Test
    void testToEntityFromCreateDto() {
        Product product = productMapper.toEntity(productCreateDto);

        assertNotNull(product);
        assertNotNull(product.getProductId());
        assertEquals(productCreateDto.getName(), product.getName());
        assertEquals(productCreateDto.getDescription(), product.getDescription());
        assertEquals(productCreateDto.getPrice(), product.getPrice());
    }

    @Test
    void testToEntityFromDetailsDto() {
        Product product = productMapper.toEntity(productDetailsDto);

        assertNotNull(product);
        assertEquals(productDetailsDto.getProductId(), product.getProductId());
        assertEquals(productDetailsDto.getName(), product.getName());
        assertEquals(productDetailsDto.getDescription(), product.getDescription());
        assertEquals(productDetailsDto.getPrice(), product.getPrice());
    }

    @Test
    void testToDto() {
        ProductDetailsDto dto = productMapper.toDto(product);

        assertNotNull(dto);
        assertEquals(product.getProductId(), dto.getProductId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
    }

    @Test
    void testToDtoList() {
        List<Product> products = Arrays.asList(product);
        List<ProductDetailsDto> dtoList = productMapper.toDtoList(products);

        assertNotNull(dtoList);
        assertEquals(products.size(), dtoList.size());
        assertEquals(products.get(0).getProductId(), dtoList.get(0).getProductId());
        assertEquals(products.get(0).getName(), dtoList.get(0).getName());
        assertEquals(products.get(0).getDescription(), dtoList.get(0).getDescription());
        assertEquals(products.get(0).getPrice(), dtoList.get(0).getPrice());
    }
}
