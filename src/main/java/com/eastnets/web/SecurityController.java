package com.eastnets.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
   @GetMapping("/")
    public String login(){
        return "login";
    }
}
