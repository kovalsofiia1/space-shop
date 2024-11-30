package com.cats.spaceshop.service.impl;

//import com.cats.spaceshop.domain.category.Category;
//import com.cats.spaceshop.repository.entity.CategoryEntity;
//import com.cats.spaceshop.dto.category.CategoryCreateDto;
//import com.cats.spaceshop.service.exception.CategoryNotFoundException;
//import com.cats.spaceshop.service.mapper.CategoryMapper;
//import org.springframework.stereotype.Service;
//import com.cats.spaceshop.service.CategoryService;
//import com.cats.spaceshop.dto.category.CategoryDto;
//import com.cats.spaceshop.repository.CategoryRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;

//@Service
//public class CategoryServiceImpl implements CategoryService {
//
//    private final CategoryRepository categoryRepository;
//    private final CategoryMapper categoryMapper;
//
//    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
//        this.categoryRepository = categoryRepository;
//        this.categoryMapper = categoryMapper;
//    }
//
//    @Override
//    public List<CategoryDto> findAll() {
//        List<CategoryEntity> categories = categoryRepository.findAll();
//        return categoryMapper.toCategoryDtoList(categories);
//    }
//
//    @Override
//    public Optional<CategoryDto> findById(UUID categoryId) {
//        return categoryRepository.findById(categoryId)
//                .map(categoryMapper::toCategoryDto);
//    }
//
//    @Override
//    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
//        Category category = categoryMapper.toCreateCategory(categoryCreateDto);
//        Category savedCategory = categoryRepository.save(category);
//        return categoryMapper.toCategoryDto(savedCategory);
//    }
//
//    @Override
//    public CategoryDto update(CategoryDto categoryDto) {
//        if (categoryDto == null || categoryDto.getCategoryId() == null) {
//            throw new IllegalArgumentException("Invalid category data");
//        }
//
//        Category existingCategory = categoryRepository.findById(categoryDto.getCategoryId())
//                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getCategoryId()));
//
//        Category newCategory = Category.builder()
//        .id(existingCategory.getId())
//        .name(categoryDto.getName() != null ? categoryDto.getName() : existingCategory.getName())
//        .description(categoryDto.getDescription() != null ? categoryDto.getDescription() : existingCategory.getDescription())
//        .build();
//
//        Category updatedCategory = categoryRepository.save(existingCategory);
//        return categoryMapper.toCategoryDto(updatedCategory);
//    }
//
//    @Override
//    public void deleteById(UUID categoryId) {
//        if (!categoryRepository.existsById(categoryId)) {
//            throw new CategoryNotFoundException(categoryId);
//        }
//        categoryRepository.deleteById(categoryId);
//    }
//}





import com.cats.spaceshop.domain.category.Category;
import com.cats.spaceshop.dto.category.CategoryCreateDto;
import com.cats.spaceshop.service.exception.CategoryNotFoundException;
import com.cats.spaceshop.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.cats.spaceshop.service.CategoryService;
import com.cats.spaceshop.dto.category.CategoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    final List<Category> categories = new ArrayList<>();

    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
        initializeCategories();

    }

    private void initializeCategories(){
        categories.add(Category.builder()
                .id(UUID.randomUUID())
                .name("Space Toys")
                .description("Toys for cats who love space adventures.")
                .build());

        categories.add(Category.builder()
                .id(UUID.randomUUID())
                .name("Galactic Treats")
                .description("Delicious treats for your space-loving cats.")
                .build());

        categories.add(Category.builder()
                .id(UUID.randomUUID())
                .name("Futuristic Scratching Posts")
                .description("Scratching posts designed for cats in the future.")
                .build());

        categories.add(Category.builder()
                .id(UUID.randomUUID())
                .name("Intergalactic Catnip")
                .description("Catnip grown in zero gravity for maximum fun.")
                .build());
    }

    @Override
    public List<CategoryDto> findAll() {
        return new ArrayList<>(categoryMapper.toCategoryDtoList(categories));
    }

    @Override
    public Optional<CategoryDto> findById(UUID categoryId) {
        return categories.stream()
                .filter(category -> category.getId().equals(categoryId))
                .findFirst()
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.toCreateCategory(categoryCreateDto);
        categories.add(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }

        Category existingCategory = categories.stream()
                .filter(cat -> cat.getId().equals(categoryDto.getCategoryId()))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getCategoryId()));

        Category updatedCategory = Category.builder()
                .id(existingCategory.getId())
                .name(categoryDto.getName() != null ? categoryDto.getName() : existingCategory.getName())
                .description(categoryDto.getDescription() != null ? categoryDto.getDescription() : existingCategory.getDescription())
                .build();

        categories.remove(existingCategory);
        categories.add(updatedCategory);

        return categoryMapper.toCategoryDto(updatedCategory);
    }

    @Override
    public void deleteById(UUID categoryId) {
        boolean removed = categories.removeIf(category -> category.getId().equals(categoryId));
        if (!removed) {
            throw new CategoryNotFoundException(categoryId);
        }
    }
}