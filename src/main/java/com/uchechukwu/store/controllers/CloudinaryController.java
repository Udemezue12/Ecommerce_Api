package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.cloudinary.CloudinaryService;
import com.uchechukwu.store.cloudinary.CloudinarySignedUploadResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Cloudinary", description = "Endpoints for Cloudinary Signed Urls")
@RequestMapping("/api/v1")
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @GetMapping("/cloudinary-signature")
    public ResponseEntity<ApiResponse<CloudinarySignedUploadResponse>> getCloudinarySignature(

            @RequestParam(required = false) Long fileSize,
            @RequestParam(required = false) String fileName
    ) {
        var cloudinary = cloudinaryService.generateImageUploadSignature("uploads", fileSize, fileName);
        return ApiResponseBuilder.success("Cloudinary signature generated successfully", cloudinary);
    }

    @GetMapping("/cloudinary-pdf-url")
    public ResponseEntity<ApiResponse<String>> generateSignedPdfUrl(

    ) {
        var publicId = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        var pdfUrl = cloudinaryService.generateSignedPdfUrl(publicId);
        return ApiResponseBuilder.success("Signed PDF URL generated successfully", pdfUrl);
    }


}
