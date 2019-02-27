package com.storyshare.boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticResourcesController {
    @GetMapping(value = {"/", "/ban", "/messages", "/home", "/login*", "/chat/*", "/story/*", "/index.html"})
    public String welcomePage() {
        return "index.html";
    }
}