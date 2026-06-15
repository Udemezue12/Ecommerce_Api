package com.uchechukwu.store.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
@Hidden
public class RedisTestController {

    private final RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/set")
    public String setRedisValue() {

        redisTemplate.opsForValue()
                .set("name", "Udemezue");

        return "Value saved to Redis";
    }


    @GetMapping("/get")
    public Object getRedisValue() {

        return redisTemplate.opsForValue()
                .get("name");
    }

    @DeleteMapping("/delete")
    public String deleteRedisValue() {

        redisTemplate.delete("name");

        return "Value deleted";
    }
}
