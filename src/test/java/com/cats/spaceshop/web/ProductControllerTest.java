package com.cats.spaceshop.web;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private UUID productId;
    private ProductDetailsDto productDetailsDto;
    private ProductCreateDto productCreateDto;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        productCreateDto = ProductCreateDto.builder()
                .name("Galactic Catnip Whiskers")
                .description("A cosmic product.")
                .categoryId("1")
                .price(new BigDecimal(12.99))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();


        productDetailsDto = ProductDetailsDto.builder()
                .productId(UUID.randomUUID())
                .name("Galactic Catnip Whiskers")
                .description("A cosmic product.")
                .categoryId("1")
                .price(new BigDecimal(12.99))
                .stockQuantity(100)
                .sku("GCW-001")
                .build();
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        List<ProductDetailsDto> products = Arrays.asList(productDetailsDto);
        when(productService.findAll()).thenReturn(products);

        ResponseEntity<List<ProductDetailsDto>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).findAll();
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {
        when(productService.findById(productId)).thenReturn(Optional.of(productDetailsDto));

        ResponseEntity<ProductDetailsDto> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDetailsDto, response.getBody());
        verify(productService, times(1)).findById(productId);
    }

    @Test
    void getProductById_shouldThrowException_whenProductDoesNotExist() {
        when(productService.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productController.getProductById(productId));
        verify(productService, times(1)).findById(productId);
    }

    @Test
    void getProductByCategory_shouldReturnProducts_whenCategoryExists() {
        String categoryId = "some-category";
        List<ProductDetailsDto> products = Arrays.asList(productDetailsDto);
        when(productService.findByCategory(categoryId)).thenReturn(Optional.of(products));

        ResponseEntity<List<ProductDetailsDto>> response = productController.getProductByCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).findByCategory(categoryId);
    }

    @Test
    void createProduct_shouldReturnCreatedStatus_whenProductCreatedSuccessfully() {
        MyApiResponse<String> apiResponse = new MyApiResponse<>(true, "Product created", null);
        when(productService.save(productCreateDto)).thenReturn(apiResponse);

        ResponseEntity<MyApiResponse<String>> response = productController.createProduct(productCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(productService, times(1)).save(productCreateDto);
    }

    @Test
    void updateProduct_shouldReturnOkStatus_whenProductUpdatedSuccessfully() {
        MyApiResponse<String> apiResponse = new MyApiResponse<>(true, "Product updated", null);
        when(productService.update(productDetailsDto)).thenReturn(apiResponse);

        ResponseEntity<MyApiResponse<String>> response = productController.updateProduct(productDetailsDto.getProductId(), productDetailsDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(productService, times(1)).update(productDetailsDto);
    }

    @Test
    void updateProduct_shouldReturnBadRequest_whenIdMismatch() {
        UUID differentId = UUID.randomUUID();

        ResponseEntity<MyApiResponse<String>> response = productController.updateProduct(differentId, productDetailsDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product ID in the request body does not match the path variable", response.getBody().getData());
    }

    @Test
    void deleteProduct_shouldReturnOkStatus_whenProductDeletedSuccessfully() {
        MyApiResponse<String> apiResponse = new MyApiResponse<>(true, "Product deleted", null);
        when(productService.deleteById(productId)).thenReturn(apiResponse);

        ResponseEntity<MyApiResponse<String>> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(productService, times(1)).deleteById(productId);
    }
}