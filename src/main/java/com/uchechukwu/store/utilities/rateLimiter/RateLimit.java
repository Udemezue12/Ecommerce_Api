package com.uchechukwu.store.utilities.rateLimiter;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    int times() default 3;

    int seconds() default 10;
}
