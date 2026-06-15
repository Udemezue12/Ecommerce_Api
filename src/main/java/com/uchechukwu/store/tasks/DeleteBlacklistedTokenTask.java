package com.uchechukwu.store.tasks;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

import com.uchechukwu.store.service.BlacklistedTokenService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteBlacklistedTokenTask {


    private final BlacklistedTokenService tokenService;

    @Job(name = "cleanup-expired-blacklisted-tokens", retries = 3)
    public void cleanupExpiredTokens() {
        tokenService.cleanupExpiredTokens();
    }
}


