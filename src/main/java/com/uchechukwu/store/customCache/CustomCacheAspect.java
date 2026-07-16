package com.uchechukwu.store.customCache;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class CustomCacheAspect {

    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    @Around("@annotation(cacheable)")
    public Object cache(
            ProceedingJoinPoint joinPoint,
            CustomCacheable cacheable
    ) throws Throwable {

        var cacheName = cacheable.value();
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

        var key = new SpelExpressionParser()
                .parseExpression(cacheable.key())
                .getValue(context, String.class);

        if (key == null) {
            return joinPoint.proceed();
        }

        Cache cache = null;

        try {
            cache = cacheManager.getCache(cacheName);

            if (cache != null) {

                var wrapper = cache.get(key);

                if (wrapper != null && wrapper.get() != null) {

                    var json = (String) wrapper.get();

                    var javaType = objectMapper.getTypeFactory()
                            .constructType(method.getGenericReturnType());

                    return objectMapper.readValue(json, javaType);
                }
            }

        } catch (DataAccessException ex) {
            log.warn("Redis unavailable.", ex);
        }

        var result = joinPoint.proceed();


        try {

            if (cache == null) {
                cache = cacheManager.getCache(cacheName);
            }

            if (cache != null) {

                var json = objectMapper.writeValueAsString(result);

                cache.put(key, json);
            }

        } catch (DataAccessException ex) {
            log.warn("Redis unavailable.", ex);
        }

        return result;
    }
}
