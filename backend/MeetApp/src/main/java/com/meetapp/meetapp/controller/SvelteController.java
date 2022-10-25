package com.meetapp.meetapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SvelteController {

    @GetMapping(value = { "", "/", "/users" })
    public String returnIndex() {
        return "forward:/index.html";
    }
}
