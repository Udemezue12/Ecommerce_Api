package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.dtos.request.ProductRequest;
import com.uchechukwu.store.dtos.request.ProductUpdateRequest;
import com.uchechukwu.store.dtos.response.ProductDto;
import com.uchechukwu.store.dtos.response.ProductsPageDto;
import com.uchechukwu.store.service.ProductService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Products", description = "Endpoints for everything product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ProductsPageDto getAllProducts(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return productService.getAllProducts(sort, "name", page, size);

    }

    @GetMapping("/products/category/{categoryId}")
    @RateLimit(times = 4, seconds = 8)

    public ProductsPageDto getProductsByCategory(@PathVariable UUID categoryId,
                                                 @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                 @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                 @RequestParam(required = false, defaultValue = "10", name = "size") int size) {

        return productService.getProductsByCategory(categoryId, sort, "name", page, size);

    }

    @GetMapping("/product/category/{categoryId}/product/{productId}")
    @RateLimit(times = 4, seconds = 8)
    public ProductDto getProductInCategory(@PathVariable UUID categoryId,
                                           @PathVariable UUID productId) {
        return productService.getSingleProductInCategory(categoryId, productId);

    }

    @GetMapping("/products/{productId}")
    @RateLimit(times = 4, seconds = 8)
    public ProductDto getSingleProduct(@PathVariable UUID productId) {
        return productService.getSingleProduct(productId);


    }

    @GetMapping("/products/filter")
    @RateLimit(times = 4, seconds = 8)
    public ProductsPageDto getProductsWithCategoryFiltering(
            @RequestParam(required = false, name = "categoryId") UUID categoryId,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return productService.fetchProductsWithCategoryFiltering(categoryId, page, size);
    }

    @PostMapping("/product/create")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductRequest request,
                                                                 UriComponentsBuilder uriBuilder) {
        return productService.createProduct(request, uriBuilder);
    }

    @PatchMapping("/product/{id}/update")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable(name = "id") UUID Id,
                                                                 @Valid @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(Id, request);
    }

    @DeleteMapping("/product/{id}/delete")
    @RateLimit(times = 4, seconds = 8)
    public void deleteProduct(@PathVariable(name = "id") UUID Id) {
        productService.deleteProduct(Id);
    }

}
