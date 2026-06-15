package com.uchechukwu.store.customCache;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
@RequiredArgsConstructor
@Component
public class CustomCacheAspect {
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    @Around("@annotation(cacheable)")
    public Object cache(
            ProceedingJoinPoint joinPoint, CustomCacheable cacheable) throws Throwable {
        var cacheName = cacheable.value();
        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();

        var context =
                new MethodBasedEvaluationContext(
                        null,
                        method,
                        joinPoint.getArgs(),
                        new DefaultParameterNameDiscoverer()
                );

        context.setBeanResolver(
                new BeanFactoryResolver(applicationContext)
        );

        var parameters = signature.getParameterNames();
        var args = joinPoint.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            context.setVariable(parameters[i], args[i]);
        }
        var key = new SpelExpressionParser()
                .parseExpression(cacheable.key())
                .getValue(context, String.class);
        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {

            assert key != null;
            var wrapper = cache.get(key);

            if (wrapper != null && wrapper.get() != null) {

                try {

                    var json = (String) wrapper.get();

                    var javaType =
                            objectMapper.getTypeFactory()
                                    .constructType(
                                            method.getGenericReturnType()
                                    );

                    return objectMapper.readValue(json, javaType);

                } catch (Exception ex) {

                    System.err.println(
                            "Cache read failed: " + ex.getMessage()
                    );
                }
            }
        }

        var result = joinPoint.proceed();

        if (cache != null) {

            try {

                var json =
                        objectMapper.writeValueAsString(result);

                cache.put(key, json);

            } catch (Exception ex) {

                System.err.println(
                        "Cache write failed: " + ex.getMessage()
                );
            }
        }

        return result;
    }


}
