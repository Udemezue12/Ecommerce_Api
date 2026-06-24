package com.uchechukwu.store.service;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.core.ComputeFileHash;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.ProductRequest;
import com.uchechukwu.store.dtos.request.ProductUpdateRequest;
import com.uchechukwu.store.dtos.response.ProductDto;
import com.uchechukwu.store.dtos.response.ProductsPageDto;
import com.uchechukwu.store.entities.Product;
import com.uchechukwu.store.entities.ProductImage;
import com.uchechukwu.store.events.MultipleImagesDeleteEvent;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.ProductImageMapper;
import com.uchechukwu.store.mappers.ProductMapper;
import com.uchechukwu.store.repositories.CategoryRepository;
import com.uchechukwu.store.repositories.ProductRepository;
import com.uchechukwu.store.sortingAndPaginating.SortAndPaginate;
import com.uchechukwu.store.validators.EntityValidator;
import com.uchechukwu.store.validators.RequestValidators;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepo;
    private final SortAndPaginate sortAndPaginate;
    private final ValidatedSortedData validSortedData;
    private final EntityValidator entityValidator;
    private final CategoryRepository categoryRepo;
    private final RequestValidators requestValidate;
    private final InventoryService inventoryService;
    private final GetCurrentUser getCurrentUser;
    private final ComputeFileHash computeFileHash;
    private final ApplicationEventPublisher applicationEventPublisher;


    public Product getProductId(UUID Id) {
        return entityValidator.findByIdOrThrow(productRepo, Id, "Product");
    }


    public Product getProductIdOrBadRequest(UUID Id) {
        return entityValidator.findByIdOrThrowBadRequest(productRepo, Id, "Product");
    }

    public Product getProductIdOrReturnNull(UUID Id) {
        return entityValidator.findByIdOrReturnNull(productRepo, Id, "Product");
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "products-by-category", key = "#categoryId + '-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    public ProductsPageDto getProductsByCategory(UUID categoryId, String sort,
                                                 String sortingValue1, int page, int size) {
        entityValidator.validateExists(categoryRepo, categoryId, "Category");
        var pageable = validSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var result = productRepo.findByCategoryId(categoryId, pageable)
                .map(ProductMapper::toManyResponse);
        return new ProductsPageDto(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );


    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "products-filtered", key = "(#categoryId != null ? #categoryId : 'all') + '-' + #page + '-' + #size")
    public ProductsPageDto fetchProductsWithCategoryFiltering(UUID categoryId,
                                                              int page, int size) {
        final Page<Product> products;
        var getSorted = sortAndPaginate.getPageableWithoutDirection(page, size, "name");
        if (categoryId != null) {
            entityValidator.validateExists(
                    categoryRepo,
                    categoryId,
                    "Category");
            products = productRepo.findByCategoryId(categoryId, getSorted);
        } else {
            products = productRepo.findAll(getSorted);
        }
        var result = products
                .map(ProductMapper::toManyResponse);
        return new ProductsPageDto(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );


    }

    @Transactional(readOnly = true)
    @Cacheable(value = "single-product-in-category", key = "#categoryId + '-' + #productId")
    public ProductDto getSingleProductInCategory(
            UUID categoryId,
            UUID productId) {


        return productRepo
                .findByIdAndCategoryId(productId, categoryId)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in this category"));
    }

    @CustomCacheable(value = "all-products", key = "#sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    @Transactional(readOnly = true)
    public ProductsPageDto getAllProducts(String sort, String sortingValue1, int page,
                                          int size) {
        var pageable = validSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var result = productRepo.findAll(pageable).map(ProductMapper::toManyResponse);
        return new ProductsPageDto(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );


    }

    @CustomCacheable(value = "single-product", key = "#productId")
    public ProductDto getSingleProduct(UUID productId) {
        return productRepo.findById(productId).map(ProductMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"all-products", "products-by-category", "products-filtered"})
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(ProductRequest request,
                                                                 UriComponentsBuilder uriBuilder) {

        var category = categoryService.getCategoryId(request.categoryId());
        var user = getCurrentUser.getCurrentUser();

        requestValidate.throwIfTrue(productRepo.findByDescriptionIgnoreCase(request.description()).isPresent(),
                "Product Description already exists");

        var product = ProductMapper.createEntity(request, category);
        if (request.getImages() != null) {
            if (request.getImages().size() > 3) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A product cannot have more than 3 images."
                );
            }
            var productImages = request.getImages().stream()
                    .map(imgReq -> {
                        var imageHash = computeFileHash.computeFileHashAsync(imgReq.imageUrl());
                        return ProductImageMapper.createImage(imgReq.imageUrl(), imgReq.resourceType(), imgReq.publicId(), imageHash.toString(), product);


                    }).toList();
            product.setImages(productImages);
            product.setThumbnailUrl(productImages.getFirst().getImageUrl());

        }
        var savedProduct = productRepo.save(product);
        inventoryService.createInventory(product, user, request.quantity());
        var productDto = ProductMapper.toResponse(savedProduct);
        return ApiResponseBuilder.created("Product created successfully", "/products/{id}", productDto.id(),
                productDto, uriBuilder);

    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"all-products", "products-by-category", "products-filtered"})
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            UUID id,
            ProductUpdateRequest request) {

        var product = getProductId(id);

        if (request.name() != null) {
            product.setName(request.name());
        }

        if (request.description() != null) {
            requestValidate.throwIfTrue(productRepo.findByDescriptionIgnoreCase(request.description()).isPresent(),
                    "Product Description already exists");
            product.setDescription(request.description());
        }

        if (request.price() != null) {
            product.setPrice(request.price());
        }

        if (request.categoryId() != null) {

            var category = categoryService.getCategoryId(
                    request.categoryId());

            product.setCategory(category);
        }
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            if (request.getImages().size() > 3) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A product cannot have more than 3 images."
                );
            }
            product.getImages().clear();
            var updatedImages = request.getImages().stream()
                    .map(imgReq -> {
                        var imageHash = computeFileHash.computeFileHashAsync(imgReq.imageUrl());
                        return ProductImageMapper.createImage(imgReq.imageUrl(), imgReq.resourceType(), imgReq.publicId(), imageHash.toString(), product);


                    }).toList();
            product.getImages().addAll(updatedImages);
        }

        if (request.quantity() != null) {
            inventoryService.updateProductQuantity(request.quantity(), product.getId());

        }

        var updatedProduct = productRepo.save(product);

        return ApiResponseBuilder.success(
                "Product updated successfully",
                ProductMapper.toResponse(updatedProduct));
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"all-products", "products-by-category", "products-filtered"})
    public void deleteProduct(UUID Id) {
        var product = getProductId(Id);
        var publicIds = product.getImages()
                .stream()
                .map(ProductImage::getPublicId)
                .filter(Objects::nonNull)
                .toList();
        var resourceTypes = product.getImages()
                .stream()
                .map(ProductImage::getResourceType)
                .filter(Objects::nonNull)
                .toList();

        applicationEventPublisher.publishEvent(new MultipleImagesDeleteEvent(product.getId(), publicIds, resourceTypes));


        productRepo.delete(product);

    }

}
