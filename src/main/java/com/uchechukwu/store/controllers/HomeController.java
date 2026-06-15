package com.uchechukwu.store.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Hidden
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("appName", appName);

        return "index";
    }

    @RequestMapping("/home")
    public String sayHello() {
        return "hello";
    }

}