package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.CategoryRequest;
import com.uchechukwu.store.dtos.response.CategoryDto;
import com.uchechukwu.store.entities.Category;

public class CategoryMapper {
    public static CategoryDto toMapper(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()

        );

    }

    public static Category createEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return category;

    }

}
