package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.repository.CategoryRepository;
import com.cats.spaceshop.repository.OrderEntryRepository;
import com.cats.spaceshop.repository.ProductRepository;
import com.cats.spaceshop.repository.entity.CategoryEntity;
import com.cats.spaceshop.repository.entity.ProductEntity;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cats.spaceshop.constants.CategoryTestConstants.CATEGORY_ENTITY;
import static com.cats.spaceshop.constants.ProductTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OrderEntryRepository orderEntryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private UUID productId;
    private ProductEntity productEntity;
    private ProductDetailsDto productDetailsDto;
    private ProductCreateDto productCreateDto;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productEntity = PRODUCT_ENTITY;
        productCreateDto = PRODUCT_CREATE_DTO;
        productDetailsDto = PRODUCT_DETAILS_DTO;

        categoryEntity = CATEGORY_ENTITY;
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.entityToDtoList(anyList())).thenReturn(List.of(productDetailsDto));

        List<ProductDetailsDto> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(productDetailsDto, result.get(0));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.entityToDto(productEntity)).thenReturn(productDetailsDto);

        Optional<ProductDetailsDto> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(productDetailsDto, result.get());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindById_NotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testSave() {
        when(categoryRepository.findById(productCreateDto.getCategoryId())).thenReturn(Optional.of(categoryEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.entityToDto(any(ProductEntity.class))).thenReturn(productDetailsDto);

        ProductDetailsDto result = productService.save(productCreateDto);

        assertNotNull(result);
        assertEquals(productDetailsDto, result);
        verify(categoryRepository, times(1)).findById(productCreateDto.getCategoryId());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testSave_CategoryNotFound() {
        when(categoryRepository.findById(productCreateDto.getCategoryId())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.save(productCreateDto));
        verify(categoryRepository, times(1)).findById(productCreateDto.getCategoryId());
    }

    @Test
    void testUpdate() {
        when(productRepository.findById(productDetailsDto.getProductId())).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.entityToDto(any(ProductEntity.class))).thenReturn(productDetailsDto);
        when(categoryRepository.findById(productDetailsDto.getCategoryId())).thenReturn(Optional.of(categoryEntity));

        ProductDetailsDto result = productService.update(productDetailsDto);

        assertNotNull(result);
        assertEquals(productDetailsDto, result);
        verify(productRepository, times(1)).findById(productDetailsDto.getProductId());
    }

    @Test
    void testUpdate_ProductNotFound() {
        when(productRepository.findById(productDetailsDto.getProductId())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(productDetailsDto));
        verify(productRepository, times(1)).findById(productDetailsDto.getProductId());
    }

    @Test
    void testDeleteById() {
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteById(productId);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteById_NotFound() {
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(productId));
        verify(productRepository, times(1)).existsById(productId);
    }

    @Test
    void testFindByCategory() {
        when(productRepository.findByCategoryId(productCreateDto.getCategoryId())).thenReturn(List.of(productEntity));
        when(productMapper.entityToDtoList(anyList())).thenReturn(List.of(productDetailsDto));

        Optional<List<ProductDetailsDto>> result = productService.findByCategory(productCreateDto.getCategoryId());

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(productDetailsDto, result.get().get(0));
        verify(productRepository, times(1)).findByCategoryId(productCreateDto.getCategoryId());
    }
}
