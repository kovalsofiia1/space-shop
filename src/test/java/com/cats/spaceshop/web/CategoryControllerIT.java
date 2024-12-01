package com.cats.spaceshop.web;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerIT extends AbstractIt {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private CategoryEntity category;
//    private CategoryCreateDto categoryCreateDto;
//
//    @BeforeEach
//    void setUp() {
//        category = new CategoryEntity();
//        category.setName("Test Category");
//        category = categoryRepository.save(category);
//
//        categoryCreateDto = CategoryCreateDto.builder()
//                .name("New Category 1")
//                .build();
//    }
//
//    @Test
//    void getAllCategories_ShouldReturnListOfCategories() throws Exception {
//        mockMvc.perform(get("/api/v1/categories")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].name").value(category.getName()));
//    }
//
//    @Test
//    void getCategory_ShouldReturnCategory_WhenFound() throws Exception {
//        mockMvc.perform(get("/api/v1/categories/" + category.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryId").value(category.getId().toString()))
//                .andExpect(jsonPath("$.name").value(category.getName()));
//    }
//
//    @Test
//    void createCategory_ShouldReturnCreatedCategory() throws Exception {
//        System.out.println(categoryCreateDto.toString());
//        mockMvc.perform(post("/api/v1/categories")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(categoryCreateDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value(categoryCreateDto.getName()));
//    }
//
//    @Test
//    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
//        category.setName("Updated Name");
//        mockMvc.perform(put("/api/v1/categories/" + category.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(category)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Updated Name"));
//    }
//
//    @Test
//    void deleteCategory_ShouldReturnSuccessMessage() throws Exception {
//        mockMvc.perform(delete("/api/v1/categories/" + category.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}


//
//import com.cats.spaceshop.dto.category.CategoryCreateDto;
//import com.cats.spaceshop.dto.category.CategoryDto;
//import com.cats.spaceshop.service.CategoryService;
//import com.cats.spaceshop.service.exception.CategoryNotFoundException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_CREATE_DTO;
//import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_DTO;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CategoryController.class)
//class CategoryControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CategoryService categoryService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private CategoryDto categoryDto;
//    private CategoryCreateDto categoryCreateDto;
//
//    @BeforeEach
//    void setUp() {
//        categoryDto = CATEGORY_DTO;
//        categoryCreateDto = CATEGORY_CREATE_DTO;}
//
//    @Test
//    void getAllCategories_ShouldReturnListOfCategories() throws Exception {
//        Mockito.when(categoryService.findAll()).thenReturn(Arrays.asList(categoryDto));
//
//        mockMvc.perform(get("/api/v1/categories")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].categoryId").value(CATEGORY_DTO.getCategoryId()))
//                .andExpect(jsonPath("$[0].name").value(CATEGORY_DTO.getName()));
//    }
//
//    @Test
//    void getCategory_ShouldReturnCategory_WhenFound() throws Exception {
//        Mockito.when(categoryService.findById(UUID.randomUUID())).thenReturn(Optional.of(categoryDto));
//
//        mockMvc.perform(get("/api/v1/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryId").value(CATEGORY_DTO.getCategoryId()))
//                .andExpect(jsonPath("$.name").value(CATEGORY_DTO.getName()));
//    }
//
//    @Test
//    void getCategory_ShouldReturn404_WhenNotFound() throws Exception {
//        Mockito.when(categoryService.findById(UUID.randomUUID())).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/v1/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createCategory_ShouldReturnCreatedCategory() throws Exception {
//        Mockito.when(categoryService.save(any(CategoryCreateDto.class))).thenReturn(categoryDto);
//
//        mockMvc.perform(post("/api/v1/categories")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(categoryCreateDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.categoryId").value(CATEGORY_DTO.getCategoryId()))
//                .andExpect(jsonPath("$.name").value(CATEGORY_DTO.getName()));
//    }
//
//    @Test
//    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
//        Mockito.when(categoryService.update(any(CategoryDto.class))).thenReturn(categoryDto);
//        UUID id = categoryDto.getCategoryId();
//        mockMvc.perform(put("/api/v1/categories/" + id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(categoryDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryId").value(CATEGORY_DTO.getCategoryId()))
//                .andExpect(jsonPath("$.name").value(CATEGORY_DTO.getName()));
//    }
//
//    @Test
//    void updateCategory_ShouldReturn400_WhenIdMismatch() throws Exception {
//        UUID id = categoryDto.getCategoryId();
//        mockMvc.perform(put("/api/v1/categories/" + id + "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(CATEGORY_DTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Category ID in the request body does not match the path variable."));
//    }
//
//    @Test
//    void deleteCategory_ShouldReturnSuccessMessage() throws Exception {
//        Mockito.doNothing().when(categoryService).deleteById(UUID.randomUUID());
//
//        mockMvc.perform(delete("/api/v1/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Category deleted successfully"));
//    }
//}
