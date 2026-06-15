package com.uchechukwu.store.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uchechukwu.store.entities.Message;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
public class MessageController {
    @RequestMapping("/get")
    public String staticMessage(){
        return "Hello Dear";
    }

    @RequestMapping("/getMessage")
    public Message getMessage(){
        return new Message("Hello World");
    }
    
}
