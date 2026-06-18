package com.uchechukwu.store.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class HomeController {


    @GetMapping("/")
    public String index() {

        return "homepage";
    }


}