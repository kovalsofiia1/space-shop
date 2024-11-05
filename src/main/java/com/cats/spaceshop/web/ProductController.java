package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Retrieve all products", description = "Get a list of all available products.")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class)))
    @GetMapping("")
    public ResponseEntity<List<ProductDetailsDto>> getAllProducts() {
        List<ProductDetailsDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(
            @Parameter(description = "Unique identifier of the product") @PathVariable UUID id) {
        ProductDetailsDto product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Get products by category", description = "Retrieve a list of products in a specific category.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "No products found for category", content = @Content)
    })
    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<ProductDetailsDto>> getProductByCategory(
            @Parameter(description = "Unique identifier of the category") @PathVariable String categoryId) {
        List<ProductDetailsDto> products = productService.findByCategory(categoryId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with categoryID: " + categoryId));
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create a new product", description = "Create a new product with specified details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict in product creation", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<ProductDetailsDto> createProduct(
         @Valid @org.springframework.web.bind.annotation.RequestBody ProductCreateDto product) {

        ProductDetailsDto response = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product", description = "Update the details of a product by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<?> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable UUID id,
            @Valid @org.springframework.web.bind.annotation.RequestBody ProductDetailsDto product) {

        if (!product.getProductId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product ID in the request body does not match the path variable.");
        }

        try {
            ProductDetailsDto response = productService.update(product);
            return ResponseEntity.ok(response);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully",content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to delete") @PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
