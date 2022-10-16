package com.meetapp.meetapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/api/users/{id}")
    public String returnUserName(@PathVariable("id") String uuid) {
        return "Jacek";
    }
}
