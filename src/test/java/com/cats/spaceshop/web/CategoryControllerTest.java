package com.cats.spaceshop.web;

import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.web.CategoryController;
import com.cats.spaceshop.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryDto = CategoryDto.builder()
                .categoryId("5")
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();

        categoryCreateDto = CategoryCreateDto.builder()
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();
    }

    @Test
    void getAllCategories_ReturnsCategoryList() {
        List<CategoryDto> categories = List.of(categoryDto);
        when(categoryService.findAll()).thenReturn(categories);

        ResponseEntity<List<CategoryDto>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void getCategory_ReturnsCategory_WhenCategoryExists() {
        String id = "1";
        CategoryDto category = categoryDto;
        when(categoryService.findById(id)).thenReturn(Optional.of(category));

        ResponseEntity<CategoryDto> response = categoryController.getCategory(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).findById(id);
    }

    @Test
    void getCategory_ReturnsNotFound_WhenCategoryDoesNotExist() {
        String id = "1";
        when(categoryService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<CategoryDto> response = categoryController.getCategory(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryService, times(1)).findById(id);
    }

    @Test
    void createCategory_ReturnsCreatedStatus() {
        CategoryCreateDto newCategory = categoryCreateDto;

        ResponseEntity<String> response = categoryController.createCategory(newCategory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category created successfully", response.getBody());
        verify(categoryService, times(1)).save(newCategory);
    }

    @Test
    void updateCategory_ReturnsOkStatus_WhenCategoryExists() {
        String id = "1";
        CategoryDto updatedCategory = categoryDto;

        ResponseEntity<String> response = categoryController.updateCategory(id, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody());
        verify(categoryService, times(1)).update(updatedCategory);
    }

    @Test
    void updateCategory_ReturnsNotFound_WhenCategoryDoesNotExist() {
        String id = "1";
        CategoryDto updatedCategory = categoryDto;
        doThrow(new ResourceNotFoundException("Category not found")).when(categoryService).update(updatedCategory);

        try {
            categoryController.updateCategory(id, updatedCategory);
        } catch (ResourceNotFoundException e) {
            assertEquals("Category not found", e.getMessage());
        }
    }

    @Test
    void deleteCategory_ReturnsOkStatus_WhenCategoryExists() {
        String id = "1";

        ResponseEntity<String> response = categoryController.deleteCategory(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService, times(1)).deleteById(id);
    }

    @Test
    void deleteCategory_ReturnsNotFound_WhenCategoryDoesNotExist() {
        String id = "1";
        doThrow(new ResourceNotFoundException("Category not found")).when(categoryService).deleteById(id);

        try {
            categoryController.deleteCategory(id);
        } catch (ResourceNotFoundException e) {
            assertEquals("Category not found", e.getMessage());
        }
    }
}
