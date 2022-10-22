package com.meetapp.meetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SvelteController {

    @GetMapping("/**/{path:[^.]*}")
    public String returnIndex() {
        return "forward:/index.html";
    }
}