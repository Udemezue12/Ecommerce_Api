package com.uchechukwu.store.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uchechukwu.store.config.CloudinaryConfig;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final Cloudinary cloudinary;
    private final CloudinaryConfig config;

    private void validateTimestamp(long timestamp) {
        var now = Instant.now().getEpochSecond();
        if ((now - timestamp) > (long) 30) {
            throw new BadRequestException("Timestamp expired");
        }
        if (timestamp > now + 5) {
            throw new BadRequestException("Timestamp is in the future");
        }
    }

    //    public boolean connect() {
//        try {
//            Map<?, ?> result =
//                    cloudinary.api().ping();
//            return "ok".equals(
//                    result.get("status"));
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new InternalServerException("Cloudinary connection failed");
//
//        }
//
//    }
    public CloudinarySignedUploadResponse generateImageUploadSignature(String folder, Long fileSize, String fileName) {
        try {
            if (fileSize != null && fileSize > MAX_FILE_SIZE) {
                throw new BadRequestException("File size is too large");
            }
            var timestamp = Instant.now().getEpochSecond();
            validateTimestamp(timestamp);
            var eager = "f_auto,q_auto";
            Map<String, Object> params = new HashMap<>();
            params.put("timestamp", timestamp);
            params.put("eager", eager);
            params.put("folder", folder);
            String signature = cloudinary.apiSignRequest(params, config.getApiSecret(), config.getSignatureVersion());
            var allowedFormats = List.of(
                    "jpg",
                    "jpeg",
                    "png",
                    "webp"
            );
            return CloudinarySignedUploadResponse.builder()
                    .signature(signature)
                    .timestamp(timestamp)
                    .apiKey(config.getApiKey())
                    .folder(folder)
                    .maxFileSize(MAX_FILE_SIZE)
                    .eager(eager)
                    .allowedFormats(allowedFormats)
                    .cloudName(
                            config.getCloudName())
                    .build();


        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("Error generating signed upload signature");
        }
    }

    public Map deleteResource(String publicId, String resourceType) {
        var options = ObjectUtils.asMap(
                "resource_type",
                resourceType,
                "invalidate",
                true
        );
        try {
            return cloudinary.uploader().destroy(publicId, options);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("Error deleting resource");

        }
    }

    public Map deleteResources(
            List<String> publicIds
    ) {

        try {

            return cloudinary.api()
                    .deleteResources(
                            publicIds,
                            ObjectUtils.asMap(
                                    "invalidate",
                                    true
                            )
                    );

        } catch (Exception e) {
            log.error(e.getMessage());

            throw new InternalServerException("Error deleting resources");
        }
    }

    public Map uploadPdf(
            File file,
            String folder,
            String publicId
    ) {

        try {

            return cloudinary.uploader()
                    .upload(
                            file,
                            ObjectUtils.asMap(
                                    "resource_type", "raw",
                                    "folder", folder,
                                    "public_id", publicId
                            )
                    );

        } catch (Exception e) {

            log.error(e.getMessage());

            throw new InternalServerException("Error uploading file");
        }
    }

    public String generateSignedPdfUrl(
            String publicId
    ) {

        try {
            return cloudinary.url()
                    .resourceType("raw")
                    .signed(true)
                    .generate(publicId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("Error generating signed upload signature");
        }
    }

    public boolean resourceExists(
            String publicId,
            String resourceType
    ) {

        try {

            cloudinary.api()
                    .resource(
                            publicId,
                            ObjectUtils.asMap(
                                    "resource_type",
                                    resourceType
                            )
                    );

            return true;

        } catch (Exception e) {

            return false;

        }
    }

}
