package com.example.evera_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SignIn")

public class SignInContoller {

    @GetMapping
    public String signIn(){
return "good";
    }

}
