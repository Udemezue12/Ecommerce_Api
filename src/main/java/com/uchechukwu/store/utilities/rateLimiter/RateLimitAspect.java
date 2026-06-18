package com.uchechukwu.store.utilities.rateLimiter;

import com.uchechukwu.store.bucketRatlimiter.BucketRateLimiter;
import com.uchechukwu.store.exceptions.RateLimitExceededException;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimitManager rateLimitManager;
    private final HttpServletRequest request;
    private final BucketRateLimiter bucketRateLimiter;

    @Around("@annotation(rateLimit)")
    public Object rateLimit(
            ProceedingJoinPoint joinPoint,
            RateLimit rateLimit) throws Throwable {

        String identifier =
                rateLimitManager.getIdentifier(request);

        String key =
                joinPoint.getSignature().getName()
                        + ":" + identifier;
        Bucket bucket =
                bucketRateLimiter.resolveBucket(
                        key,
                        rateLimit.times(),
                        rateLimit.seconds());

        if (!bucket.tryConsume(1)) {

            throw new RateLimitExceededException(
                    "Bucket limit exceeded");
        }

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