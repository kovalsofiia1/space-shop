//package com.cats.spaceshop.service.impl;
//
//import com.cats.spaceshop.repository.entity.CategoryEntity;
//import com.cats.spaceshop.dto.category.CategoryCreateDto;
//import com.cats.spaceshop.dto.category.CategoryDto;
//import com.cats.spaceshop.service.exception.CategoryNotFoundException;
//import com.cats.spaceshop.service.mapper.CategoryMapper;
//import com.cats.spaceshop.repository.CategoryRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_CREATE_DTO;
//import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_DTO;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class CategoryServiceImplTest {
//
//    @MockBean
//    private CategoryRepository categoryRepository;
//
//    @MockBean
//    private CategoryMapper categoryMapper;
//
//    @Autowired
//    private CategoryServiceImpl categoryService;
//
//    private CategoryEntity categoryEntity;
//    private CategoryDto categoryDto;
//    private CategoryCreateDto categoryCreateDto;
//
//    private UUID categoryId;
//
//    @BeforeEach
//    public void setUp() {
//        categoryDto = CATEGORY_DTO;
//        categoryId = CATEGORY_DTO.getCategoryId();
//        categoryEntity = categoryMapper.dtoToEntity(CATEGORY_DTO);
//
//        categoryCreateDto = CATEGORY_CREATE_DTO;
//    }
//
//    @Test
//    void shouldFindAllCategories() {
//        // Arrange
//        when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity));
//        when(categoryMapper.entityToCategoryDtoList(anyList())).thenReturn(List.of(categoryDto));
//
//        // Act
//        var categories = categoryService.findAll();
//
//        // Assert
//        assertNotNull(categories);
//        assertEquals(1, categories.size());
//        assertEquals(categoryEntity.getName(), categories.get(0).getName());
//        verify(categoryRepository, times(1)).findAll();
//    }
//
//    @Test
//    void shouldFindCategoryById() {
//        // Arrange
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//        when(categoryMapper.entityToCategoryDto(any(CategoryEntity.class))).thenReturn(categoryDto);
//
//        // Act
//        var result = categoryService.findById(categoryId);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(categoryEntity.getName(), result.get().getName());
//        verify(categoryRepository, times(1)).findById(categoryId);
//    }
//
//    @Test
//    void shouldReturnEmptyWhenCategoryNotFoundById() {
//        // Arrange
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
//
//        // Act
//        var result = categoryService.findById(categoryId);
//
//        // Assert
//        assertFalse(result.isPresent());
//        verify(categoryRepository, times(1)).findById(categoryId);
//    }
//
//    @Test
//    void shouldSaveCategory() {
//        // Arrange
//        when(categoryMapper.entityToCreateCategory(categoryCreateDto)).thenReturn(categoryEntity);
//        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
//        when(categoryMapper.entityToCategoryDto(categoryEntity)).thenReturn(categoryDto);
//
//        // Act
//        var savedCategory = categoryService.save(categoryCreateDto);
//
//        // Assert
//        assertNotNull(savedCategory);
//        assertEquals("Cosmic Catnip", savedCategory.getName());
//        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
//    }
//
//    @Test
//    void shouldUpdateCategory() {
//        // Arrange
////        categoryDto.setName("Updated Catnip");
//        CategoryDto updatedDto = CategoryDto.builder()
//                .name("Updated Catnip")
//                .description(categoryDto.getDescription())
//                .build();
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
//        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
//        when(categoryMapper.entityToCategoryDto(any(CategoryEntity.class))).thenReturn(categoryDto);
//
//        // Act
//        var updatedCategory = categoryService.update(categoryDto);
//
//        // Assert
//        assertNotNull(updatedCategory);
//        assertEquals("Updated Catnip", updatedCategory.getName());
//        verify(categoryRepository, times(1)).findById(categoryId);
//        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenUpdatingNonExistingCategory() {
//        // Arrange
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(categoryDto));
//        verify(categoryRepository, times(1)).findById(categoryId);
//    }
//
//    @Test
//    void shouldDeleteCategoryById() {
//        // Arrange
//        when(categoryRepository.existsById(categoryId)).thenReturn(true);
//
//        // Act
//        categoryService.deleteById(categoryId);
//
//        // Assert
//        verify(categoryRepository, times(1)).deleteById(categoryId);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenDeletingNonExistingCategory() {
//        // Arrange
//        when(categoryRepository.existsById(categoryId)).thenReturn(false);
//
//        // Act & Assert
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(categoryId));
//        verify(categoryRepository, times(1)).existsById(categoryId);
//    }
//}



package com.cats.spaceshop.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Category Service Tests with Testcontainers")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceImplIT extends AbstractIt {

////    @MockBean
//    @Autowired private CategoryService categoryService;
//
//    @SpyBean
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private static UUID newCategoryId;
//    private static String newCategoryName = "Test Category";
//    private static String updatedCategoryName = "Updated Test Category";
//
//    @AfterEach
//    void cleanUp() {
//        categoryRepository.deleteAll();
//    }
//
//    @BeforeEach
//    void setUp() {
//        reset(categoryRepository);
//
//        // Initial setup of category
//        CategoryEntity category1 = CategoryEntity.builder()
//                .name("Star Category 1")
//                .description("Star Test Category 1")
//                .build();
//        categoryRepository.save(category1);
//
//        newCategoryId = category1.getId();
//    }
//
//    @Test
//    @Order(1)
//    void shouldReturnAllCategories() {
//        List<CategoryDto> categories = categoryService.findAll();
//        assertNotNull(categories);
//        assertEquals(1, categories.size());
//    }
//
//    @Test
//    @Order(2)
//    void shouldReturnCategoryById() {
//        CategoryDto categoryDto = categoryService.findById(newCategoryId).orElse(null);
//        assertNotNull(categoryDto);
//        assertEquals("Star Category 1", categoryDto.getName());
//    }
//
//    @Test
//    @Order(3)
//    void shouldThrowCategoryNotFoundExceptionWhenCategoryNotFoundById() {
//        UUID randomId = UUID.randomUUID();
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(randomId).orElseThrow(() -> new CategoryNotFoundException(randomId)));
//    }
//
//    @Test
//    @Order(4)
//    void shouldCreateCategory() {
//        CategoryCreateDto newCategoryCreateDto = CategoryCreateDto.builder()
//                .name(newCategoryName)
//                .description("Test category description")
//                .build();
//
//        CategoryDto createdCategory = categoryService.save(newCategoryCreateDto);
//        assertNotNull(createdCategory);
//        assertEquals(newCategoryName, createdCategory.getName());
//    }
//
//    @Test
//    @Order(5)
//    void shouldUpdateCategory() {
//        CategoryDto existingCategory = categoryService.findById(newCategoryId).orElse(null);
//        assertNotNull(existingCategory);
//
//        CategoryDto newCategory = CategoryDto.builder()
//                .categoryId(existingCategory.getCategoryId())
//                .name(updatedCategoryName)
//                .build();
//
////        existingCategory.setName(updatedCategoryName);
//        CategoryDto updatedCategory = categoryService.update(newCategory);
//        assertNotNull(updatedCategory);
//        assertEquals(updatedCategoryName, updatedCategory.getName());
//    }
//
//    @Test
//    @Order(6)
//    void shouldThrowCategoryNotFoundExceptionWhenUpdatingNonExistentCategory() {
//        CategoryDto nonExistentCategory = CategoryDto.builder()
//                .categoryId(UUID.randomUUID())
//                .name("Nonexistent Category")
//                .build();
//
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(nonExistentCategory));
//    }
//
//    @Test
//    @Order(7)
//    void shouldDeleteCategoryById() {
//        categoryService.deleteById(newCategoryId);
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(newCategoryId).orElseThrow(() -> new CategoryNotFoundException(newCategoryId)));
//    }
//
//    @Test
//    @Order(8)
//    void shouldThrowCategoryNotFoundExceptionWhenDeletingNonExistentCategory() {
//        UUID randomId = UUID.randomUUID();
//        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(randomId));
//    }
}
