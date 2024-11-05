package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        String id = categoryDto.getCategoryId();
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
        when(categoryService.findById(id)).thenThrow(new CategoryNotFoundException("Category not found with ID: " + id));

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.getCategory(id);
        });

        assertEquals("Category not found with ID: " + id, exception.getMessage());
        verify(categoryService, times(1)).findById(id);
    }

    @Test
    void createCategory_ReturnsCreatedStatus() {
        CategoryCreateDto newCategory = categoryCreateDto;

        when(categoryService.save(newCategory)).thenReturn(categoryDto);
        ResponseEntity<CategoryDto> response = categoryController.createCategory(newCategory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService, times(1)).save(newCategory);
    }


    @Test
    void updateCategory_ReturnsOkStatus_WhenCategoryExists() {
        String categoryId = "5";
        CategoryDto inputDto = CategoryDto.builder()
                .categoryId(categoryId)
                .name("Updated Astro Beds")
                .description("Updated description for stargazing cats.")
                .build();

        when(categoryService.update(inputDto)).thenReturn(inputDto);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, inputDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inputDto, response.getBody());
        verify(categoryService, times(1)).update(inputDto);
    }

    @Test
    void updateCategory_ReturnsNotFound_WhenCategoryDoesNotExist() {
        String id = "1"; // Assuming this ID should not exist
        CategoryDto updatedCategory = CategoryDto.builder()
                .categoryId(id)
                .name("Astro Beds")
                .description("Beds for cats who love stargazing.")
                .build();

        // Ensure the service will throw an exception for this ID
        doThrow(new CategoryNotFoundException("Category not found for update: " + id))
                .when(categoryService).update(updatedCategory);

        // Act & Assert: Expect the exception to be thrown when calling the controller
        CategoryNotFoundException thrown = assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.updateCategory(id, updatedCategory);
        });

        // Assert the exception message
        assertEquals("Category not found for update: " + id, thrown.getMessage());
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
        doThrow(new CategoryNotFoundException("Category not found")).when(categoryService).deleteById(id);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.deleteCategory(id);
        });
    }
}
