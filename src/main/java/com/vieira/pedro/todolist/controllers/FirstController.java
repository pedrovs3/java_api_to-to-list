package com.vieira.pedro.todolist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class FirstController {
    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }
}
