package com.uchechukwu.store.jobrunr;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@Hidden
@Tag(name = "JobRunr Jobs")
public class JobRunrController {

    private final JobRunrManagementService jobRunrManagementService;


    @PostMapping("/delete/successful")
    @RateLimit(times = 4, seconds = 8)

    public ResponseEntity<ApiResponse<Integer>> deleteSucceededJobs() {
        var response = jobRunrManagementService.purgeSucceededJobs();
        return ApiResponseBuilder.success("Jobs deleted successfully", response);


    }

    @PostMapping("/delete/deleted")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<Integer>> deleteDeletedJobs() {
        var response = jobRunrManagementService.purgeDeletedJobs();
        return ApiResponseBuilder.success("Jobs deleted successfully", response);


    }
}
