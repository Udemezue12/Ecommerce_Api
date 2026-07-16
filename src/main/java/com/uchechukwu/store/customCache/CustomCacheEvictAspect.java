package com.uchechukwu.store.customCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomCacheEvictAspect {

    private final CacheManager cacheManager;
    private final ApplicationContext applicationContext;

    @AfterReturning(
            value = "@annotation(evict)",
            returning = "result"
    )
    public void evict(
            JoinPoint joinPoint,
            CustomCacheEvict evict,
            Object result
    ) {

        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();

        var context = new MethodBasedEvaluationContext(
                null,
                method,
                joinPoint.getArgs(),
                new DefaultParameterNameDiscoverer()
        );

        context.setBeanResolver(new BeanFactoryResolver(applicationContext));

        var parameters = signature.getParameterNames();
        var args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            context.setVariable(parameters[i], args[i]);
        }

        var key = evict.key().isBlank()
                ? null
                : new SpelExpressionParser()
                .parseExpression(evict.key())
                .getValue(context, String.class);

        for (String cacheName : evict.cacheNames()) {

            try {

                var cache = cacheManager.getCache(cacheName);

                if (cache == null) {
                    continue;
                }

                if (evict.allEntries()) {
                    cache.clear();
                } else if (key != null) {
                    cache.evict(key);
                }

            } catch (Exception ex) {
                log.warn("Failed to evict cache '{}'", cacheName, ex);
            }
        }
    }
}