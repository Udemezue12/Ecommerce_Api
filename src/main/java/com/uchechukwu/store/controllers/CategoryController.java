package com.uchechukwu.store.controllers;

import com.uchechukwu.store.dtos.request.CategoryRequest;
import com.uchechukwu.store.dtos.request.CategoryUpdateRequest;
import com.uchechukwu.store.dtos.response.CategoryDto;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category", description = "Endpoints for category creation and management")
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> getCategorySummaries(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort) {
        return categoryService.getAllCategories(sort, "name");

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> findCategoryDtoId(@PathVariable UUID id) {
        CategoryDto category = categoryService.getSingleCategory(id);
        return ApiResponseBuilder.success(
                "Category fetched successfully",
                category);
    }

    @PostMapping("/category/create")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PatchMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@PathVariable(name = "id") UUID Id,
                                                                   @Valid @RequestBody CategoryUpdateRequest request) {
        return categoryService.updateCategory(Id, request);
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable(name = "id") UUID Id) {
        return categoryService.deleteCategory(Id);
    }

}
