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

    private static final ProductCreateDto DEFAULT_PRODUCT_CREATE_DTO = buildProductCreateDto(
            "Cat Toy",
            "A fun toy for cats.",
            new BigDecimal(9.99),
            100,
            "CT-001"
    );
    private static final Product DEFAULT_PRODUCT = buildProduct(
            UUID.randomUUID(),
            "Cat Toy",
            "A fun toy for cats.",
            new BigDecimal(9.99),
            100,
            "CT-001"
    );
    private static final ProductDetailsDto DEFAULT_PRODUCT_DETAILS_DTO = buildProductDetailsDto(DEFAULT_PRODUCT);


    public static ProductCreateDto buildProductCreateDto(String name, String description, BigDecimal price, Integer stockQuantity, String sku){
        return ProductCreateDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .sku(sku)
                .build();
    }

    public static Product buildProduct(UUID id, String name, String description, BigDecimal price, Integer stockQuantity, String sku){
        return Product.builder()
                .productId(id)
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .sku(sku)
                .build();
    }

    public static ProductDetailsDto buildProductDetailsDto(Product product){
        return ProductDetailsDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .sku(product.getSku())
                .build();
    }

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    void testToEntityFromCreateDto() {
        Product product = productMapper.toEntity(DEFAULT_PRODUCT_CREATE_DTO);

        assertNotNull(product);
        assertNotNull(product.getProductId());
        assertEquals(DEFAULT_PRODUCT_CREATE_DTO.getName(), product.getName());
        assertEquals(DEFAULT_PRODUCT_CREATE_DTO.getDescription(), product.getDescription());
        assertEquals(DEFAULT_PRODUCT_CREATE_DTO.getPrice(), product.getPrice());
    }

    @Test
    void testToEntityFromDetailsDto() {
        Product product = productMapper.toEntity(DEFAULT_PRODUCT_DETAILS_DTO);

        assertNotNull(product);
        assertEquals(DEFAULT_PRODUCT_DETAILS_DTO.getProductId(), product.getProductId());
        assertEquals(DEFAULT_PRODUCT_DETAILS_DTO.getName(), product.getName());
        assertEquals(DEFAULT_PRODUCT_DETAILS_DTO.getDescription(), product.getDescription());
        assertEquals(DEFAULT_PRODUCT_DETAILS_DTO.getPrice(), product.getPrice());
    }

    @Test
    void testToDto() {
        ProductDetailsDto dto = productMapper.toDto(DEFAULT_PRODUCT);

        assertNotNull(dto);
        assertEquals(DEFAULT_PRODUCT.getProductId(), dto.getProductId());
        assertEquals(DEFAULT_PRODUCT.getName(), dto.getName());
        assertEquals(DEFAULT_PRODUCT.getDescription(), dto.getDescription());
        assertEquals(DEFAULT_PRODUCT.getPrice(), dto.getPrice());
    }

    @Test
    void testToDtoList() {
        List<Product> products = Arrays.asList(DEFAULT_PRODUCT);
        List<ProductDetailsDto> dtoList = productMapper.toDtoList(products);

        assertNotNull(dtoList);
        assertEquals(products.size(), dtoList.size());
        assertEquals(products.get(0).getProductId(), dtoList.get(0).getProductId());
        assertEquals(products.get(0).getName(), dtoList.get(0).getName());
        assertEquals(products.get(0).getDescription(), dtoList.get(0).getDescription());
        assertEquals(products.get(0).getPrice(), dtoList.get(0).getPrice());
    }
}
