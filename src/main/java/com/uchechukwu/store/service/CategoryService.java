package com.uchechukwu.store.service;

import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.CategoryRequest;
import com.uchechukwu.store.dtos.request.CategoryUpdateRequest;
import com.uchechukwu.store.dtos.response.CategoryDto;
import com.uchechukwu.store.entities.Category;
import com.uchechukwu.store.mappers.CategoryMapper;
import com.uchechukwu.store.repositories.CategoryRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.validators.EntityValidator;
import com.uchechukwu.store.validators.RequestValidators;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepo;
    private final RequestValidators requestValidate;
    private final EntityValidator entityValidator;

    private final ValidatedSortedData validSortedData;

    public Category getCategoryId(UUID Id) {
        return entityValidator.findByIdOrThrow(categoryRepo, Id, "Category");
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "categories", key = "#all")
    public List<CategoryDto> getAllCategories(String sort, String sortingValue1) {
        var sorting = validSortedData.getValidatedSortedData(sort, sortingValue1);
        return categoryRepo.findAll(sorting).stream().map(CategoryMapper::toMapper).toList();

    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "category", key = "#categoryId")
    public CategoryDto getSingleCategory(UUID categoryId) {
        System.out.println("FETCHING FROM DATABASE");
        return categoryRepo.findById(categoryId).map(CategoryMapper::toMapper)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"categories", "category"})
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(CategoryRequest request) {
        requestValidate.throwIfTrue(categoryRepo.findByNameIgnoreCase(request.name()).isPresent(),
                "Category already exists");
        var category = CategoryMapper.createEntity(request);

        var savedCategory = categoryRepo.save(category);
        var categoryDto = CategoryMapper.toMapper(savedCategory);
        return ApiResponseBuilder.success("Category Created Successfully", categoryDto);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"categories", "category"})
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(UUID categoryId, CategoryUpdateRequest request) {
        var category = getCategoryId(categoryId);
        if (request.name() != null) {
            requestValidate.throwIfTrue(categoryRepo.findByNameIgnoreCase(request.name()).isPresent(),
                    "Category already exists");
            category.setName(request.name());
        }
        var updatedCategory = categoryRepo.save(category);
        var categoryDto = CategoryMapper.toMapper(updatedCategory);
        return ApiResponseBuilder.success("Category Updated Successfully", categoryDto);

    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"categories", "category"})
    public ResponseEntity<ApiResponse<Void>> deleteCategory(UUID categoryId) {
        var category = getCategoryId(categoryId);
        categoryRepo.delete(category);
        return ApiResponseBuilder.success("Deleted Successfully", null);
    }

}
