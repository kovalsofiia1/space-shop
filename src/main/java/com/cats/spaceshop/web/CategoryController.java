package com.cats.spaceshop.web;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.CategoryService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String id) {
        return categoryService.findById(id)
                .map(categoryDto -> ResponseEntity.ok(categoryDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable String id, @Valid @RequestBody CategoryDto categoryDto) {
        categoryService.update(categoryDto);
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id, @Valid @RequestBody CategoryDto categoryDto) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

}