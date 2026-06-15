package com.uchechukwu.store.tasks;

import com.uchechukwu.store.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CloudinaryImageDeleteTasks {
    private final CloudinaryService cloudinaryService;

    @Job(name = "Delete multiple images from Cloudinary", retries = 3)
    public void deleteImages(List<String> publicIds, List<String> resourceTypes) {
        if (publicIds.size() != resourceTypes.size()) {
            throw new IllegalArgumentException("Lists must have the same size");
        }

        for (int i = 0; i < publicIds.size(); i++) {
            cloudinaryService.deleteResource(publicIds.get(i), resourceTypes.get(i));
        }

//        cloudinaryService.deleteResources(publicIds);
    }

    @Job(name = "Delete image from Cloudinary", retries = 3)
    public void deleteImage(String publicId, String resourceType) {


        cloudinaryService.deleteResource(publicId, resourceType);


    }
}
