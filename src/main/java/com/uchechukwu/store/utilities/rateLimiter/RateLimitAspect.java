package com.uchechukwu.store.utilities.rateLimiter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.uchechukwu.store.exceptions.RateLimitExceededException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimitManager rateLimitManager;
    private final HttpServletRequest request;

    @Around("@annotation(rateLimit)")
    public Object rateLimit(
            ProceedingJoinPoint joinPoint,
            RateLimit rateLimit) throws Throwable {

        String identifier =
                rateLimitManager.getIdentifier(request);

        String key =
                joinPoint.getSignature().getName()
                        + ":" + identifier;

        boolean limited =
                rateLimitManager.isRateLimited(
                        key,
                        rateLimit.times(),
                        rateLimit.seconds());

        if (limited) {

            throw new RateLimitExceededException(
                    "Rate limit exceeded. Please try again later.");
        }

        return joinPoint.proceed();
    }
}