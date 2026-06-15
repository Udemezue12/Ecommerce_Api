package com.uchechukwu.store.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
@RequestMapping("/api")
public class TestController {
    @GetMapping("/headers")
    public String getHeaders(@RequestHeader("Authorization") String authHeader,
                             @RequestHeader(value = "X-Device", required = false) String device) {
        return "Auth: " + authHeader + " | Device: " + device;

    }

}
