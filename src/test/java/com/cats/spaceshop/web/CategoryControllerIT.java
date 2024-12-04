package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.dto.category.CategoryDto;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_CREATE_DTO;
import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;

    @BeforeEach
    void setUp() {
        categoryDto = CATEGORY_DTO;
        categoryCreateDto = CATEGORY_CREATE_DTO;}

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

//    @Test
//    void getCategory_ShouldReturnCategory_WhenFound() throws Exception {
//        Mockito.when(categoryService.findById("1")).thenReturn(Optional.of(categoryDto));
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
//        Mockito.when(categoryService.findById("1")).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/v1/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

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

//    @Test
//    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
//        Mockito.when(categoryService.update(any(CategoryDto.class))).thenReturn(categoryDto);
//        String id = categoryDto.getCategoryId();
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
//        String id = categoryDto.getCategoryId();
//        mockMvc.perform(put("/api/v1/categories/" + id + "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(CATEGORY_DTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Category ID in the request body does not match the path variable."));
//    }

//    @Test
//    void deleteCategory_ShouldReturnSuccessMessage() throws Exception {
//        Mockito.doNothing().when(categoryService).deleteById("1");
//
//        mockMvc.perform(delete("/api/v1/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Category deleted successfully"));
//    }
}
