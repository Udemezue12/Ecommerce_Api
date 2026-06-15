package com.uchechukwu.store.customCache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CustomCacheEvictAspect {

    private final CacheManager cacheManager;

    @AfterReturning(
            value = "@annotation(evict)",
            returning = "result"
    )
    public void evict(
            CustomCacheEvict evict,
            Object result
    ) {

        for (String cacheName : evict.cacheNames()) {

            var cache = cacheManager.getCache(cacheName);

            if (cache == null) {
                continue;
            }

            if (evict.allEntries()) {
                cache.clear();
            }
        }
    }
}
