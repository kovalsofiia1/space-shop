package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Retrieve all categories", description = "Fetch a list of all available categories.")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)))
    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Retrieve a category by ID", description = "Fetch details of a category using its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(
            @Parameter(description = "Unique identifier of the category") @PathVariable String id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    @Operation(summary = "Create a new category", description = "Create a new category with the specified details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @org.springframework.web.bind.annotation.RequestBody CategoryCreateDto categoryCreateDto) {
        CategoryDto created = categoryService.save(categoryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing category", description = "Update the details of a category by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @Parameter(description = "ID of the category to update") @PathVariable String id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CategoryDto categoryDto) {

        if (!categoryDto.getCategoryId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Category ID in the request body does not match the path variable.");
        }

        try {
            CategoryDto updated = categoryService.update(categoryDto);
            return ResponseEntity.ok(updated);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @Operation(summary = "Delete a category", description = "Delete a category by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @Parameter(description = "ID of the category to delete") @PathVariable String id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
