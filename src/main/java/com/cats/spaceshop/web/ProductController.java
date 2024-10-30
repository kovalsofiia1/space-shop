package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.ApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.web.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDetailsDto>> getAllProducts() {
        List<ProductDetailsDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(@PathVariable UUID id) {
        ProductDetailsDto product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return ResponseEntity.ok(product);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<ProductDetailsDto>> getProductByCategory(@PathVariable String categoryId) {
        List<ProductDetailsDto> products = productService.findByCategory(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with categoryID: " + categoryId));
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> createProduct(@Valid @RequestBody ProductCreateDto product) {
        ApiResponse<String> response = productService.save(product);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // Handle conflicts appropriately
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@Valid @RequestBody ProductDetailsDto product, @PathVariable UUID id) {
        if (!product.getProductId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Product ID in the request body does not match the path variable", null));
        }

        ApiResponse<String> response = productService.update(product);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // Handle conflicts appropriately
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable UUID id) {
        ApiResponse<String> response = productService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
